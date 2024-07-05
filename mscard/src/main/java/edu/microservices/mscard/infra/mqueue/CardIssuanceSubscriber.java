package edu.microservices.mscard.infra.mqueue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.microservices.mscard.domain.Card;
import edu.microservices.mscard.domain.CardClient;
import edu.microservices.mscard.domain.DataRequestCard;
import edu.microservices.mscard.repository.CardRepository;
import edu.microservices.mscard.repository.ClientCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class CardIssuanceSubscriber {

    private final CardRepository cardRepository;
    private final ClientCardRepository clientCardRepository;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "${mq.queues.card-issuance}")
    public void receiveIssuanceRequest(@Payload String payload) {
        try {
            DataRequestCard data = objectMapper.readValue(payload, DataRequestCard.class);
            Card card = cardRepository.findById(data.getIdCard())
                    .orElseThrow(() -> new NoSuchElementException("Cartão não encontrado com ID: " + data.getIdCard()));

            CardClient cardClient = new CardClient();
            cardClient.setCard(card);
            cardClient.setIdClient(data.getIdClient());
            cardClient.setLimitApproved(data.getLimitApproved());

            clientCardRepository.save(cardClient);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao processar JSON: " + e.getMessage(), e);
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Cartão não encontrado: " + e.getMessage(), e);
        }
    }
}

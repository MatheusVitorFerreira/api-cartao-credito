package com.mscreditevaluator.service;

import com.mscreditevaluator.domain.info.*;
import com.mscreditevaluator.domain.issuance.ApprovedCard;
import com.mscreditevaluator.domain.issuance.DataRequestCard;
import com.mscreditevaluator.domain.issuance.ReturnCustomerReview;
import com.mscreditevaluator.expection.erros.DataClientNotFoundExcption;
import com.mscreditevaluator.expection.erros.ErroRequestCardException;
import com.mscreditevaluator.expection.erros.ErrorCommunicationMicroservicesException;
import com.mscreditevaluator.infra.clients.CardResourceClient;
import com.mscreditevaluator.infra.clients.ClientResourceClient;
import com.mscreditevaluator.infra.mqueue.PublisherCardIssuanceRequest;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CrediteValuatorService {

    private final ClientResourceClient clientResourceClient;
    private final CardResourceClient cardResourceClient;
    private final PublisherCardIssuanceRequest publisherCardIssuanceRequest;

    public CustomerSituation getCustomerSituation(String idClient)
            throws DataClientNotFoundExcption, ErrorCommunicationMicroservicesException {
        try {
            ResponseEntity<DataClient> dataClientResponse = clientResourceClient.getClientById(idClient);
            ResponseEntity<List<CardClient>> cardResponse = cardResourceClient.getCartoesByCliente(idClient);

            if (dataClientResponse.getStatusCode().is2xxSuccessful() && cardResponse.getStatusCode().is2xxSuccessful()) {
                DataClient dataClient = dataClientResponse.getBody();
                List<CardClient> cards = cardResponse.getBody();

                log.info("Dados do cliente e cartões obtidos com sucesso.");

                return CustomerSituation.builder()
                        .client(dataClient)
                        .cards(cards)
                        .build();
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não foi possível obter dados do cliente e/ou cartões");
            }
        } catch (Exception e) {
            log.error("Erro ao obter situação do cliente: {}", e.getMessage());
            throw new ErrorCommunicationMicroservicesException("Erro ao obter situação do cliente: " + e.getMessage());
        }
    }

    public ReturnCustomerReview performAssessment(String idClient, Long income)
            throws DataClientNotFoundExcption, ErrorCommunicationMicroservicesException {
        try {
            ResponseEntity<DataClient> dataClientResponse = clientResourceClient.getClientById(idClient);

            if (!dataClientResponse.getStatusCode().is2xxSuccessful()) {
                throw new DataClientNotFoundExcption("Cliente não encontrado");
            }

            DataClient dataClient = dataClientResponse.getBody();

            BigDecimal percentage = BigDecimal.valueOf(0.30);
            BigDecimal limitApproved = BigDecimal.valueOf(dataClient.getIncome()).multiply(percentage);

            ResponseEntity<List<DataCard>> cardResponse = cardResourceClient.getCardIncomeMax(income);

            if (!cardResponse.getStatusCode().is2xxSuccessful()) {
                throw new ErrorCommunicationMicroservicesException("Erro ao obter dados dos cartões");
            }

            List<DataCard> dataCards = cardResponse.getBody();

            List<ApprovedCard> approvedCards = dataCards.stream().map(dataCard -> {
                ApprovedCard approvedCard = new ApprovedCard();
                approvedCard.setName(dataCard.getCardName());
                approvedCard.setBrand(dataCard.getCreditCardBrand());
                approvedCard.setLimitApproved(limitApproved);
                return approvedCard;
            }).collect(Collectors.toList());

            log.info("Avaliação do cliente concluída com sucesso.");

            return new ReturnCustomerReview(approvedCards);

        } catch (FeignException.FeignClientException e) {
            log.error("Erro de comunicação com microserviço: {}", e.getMessage());
            throw new ErrorCommunicationMicroservicesException("Erro de comunicação com microserviço");
        } catch (DataClientNotFoundExcption e) {
            log.error("Cliente não encontrado: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Erro ao realizar a avaliação do cliente: {}", e.getMessage());
            throw new ErrorCommunicationMicroservicesException("Erro ao realizar a avaliação do cliente");
        }
    }

    public ProtocolRequestCard requestCardIssuance (DataRequestCard data) {
        try {
            publisherCardIssuanceRequest.requestCard(data);
            var protocol = UUID.randomUUID().toString();

            log.info("Requisição de emissão de cartão enviada com sucesso. Protocolo: {}", protocol);

            return new ProtocolRequestCard(protocol);
        } catch (Exception e) {
            log.error("Erro ao enviar requisição de emissão de cartão: {}", e.getMessage());
            throw new ErroRequestCardException(e.getMessage());
        }
    }
}

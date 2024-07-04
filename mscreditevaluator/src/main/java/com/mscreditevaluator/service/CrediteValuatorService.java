package com.mscreditevaluator.service;

import com.mscreditevaluator.domain.info.CustomerSituation;
import com.mscreditevaluator.domain.info.DataCard;
import com.mscreditevaluator.domain.info.DataClient;

import com.mscreditevaluator.domain.issuance.ApprovedCard;
import com.mscreditevaluator.domain.issuance.CardClient;
import com.mscreditevaluator.domain.issuance.ReturnCustomerReview;
import com.mscreditevaluator.expection.erros.DataClientNotFoundExcption;
import com.mscreditevaluator.expection.erros.ErrorCommunicationMicroservicesException;
import com.mscreditevaluator.infra.clients.CardResourceClient;
import com.mscreditevaluator.infra.clients.ClientResourceClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
@RequiredArgsConstructor
public class CrediteValuatorService {

    private static final Logger logger = LoggerFactory.getLogger(CrediteValuatorService.class);

    private final ClientResourceClient clientResourceClient;
    private final CardResourceClient cardResourceClient;

    public CustomerSituation getCustomerSituation(String idClient) {
        try {
            ResponseEntity<DataClient> dataClientResponse = clientResourceClient.getClientById(idClient);
            ResponseEntity<List<CardClient>> cardResponse = cardResourceClient.getCartoesByCliente(idClient);

            if (dataClientResponse.getStatusCode().is2xxSuccessful() && cardResponse.getStatusCode().is2xxSuccessful()) {
                DataClient dataClient = dataClientResponse.getBody();
                List<CardClient> cards = cardResponse.getBody();

                return CustomerSituation.builder()
                        .client(dataClient)
                        .cards(cards)
                        .build();
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não foi possível obter dados do cliente e/ou cartões");
            }
        } catch (Exception e) {
            logger.error("Erro ao obter situação do cliente: {}", e.getMessage());
            throw e;
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

            List<DataCard> cards = cardResponse.getBody();

            List<ApprovedCard> approvedCards = cards.stream().map(card -> {
                ApprovedCard approvedCard = new ApprovedCard();
                approvedCard.setCard(card.getCardName());
                approvedCard.setCreditCardBrand(card.getCreditCardBrand());
                approvedCard.setLimitApproved(limitApproved); // Definindo o limite aprovado como 30% da renda
                return approvedCard;
            }).collect(Collectors.toList());

            return new ReturnCustomerReview(approvedCards);

        } catch (FeignException.FeignClientException e) {
            throw new ErrorCommunicationMicroservicesException("Erro de comunicação com microserviço");
        } catch (DataClientNotFoundExcption e) {
            throw e;
        } catch (Exception e) {
            logger.error("Erro ao realizar a avaliação do cliente: {}", e.getMessage());
            throw new ErrorCommunicationMicroservicesException("Erro ao realizar a avaliação do cliente");
        }
    }
}

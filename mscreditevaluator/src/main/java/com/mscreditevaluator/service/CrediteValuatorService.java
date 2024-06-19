package com.mscreditevaluator.service;

import com.mscreditevaluator.domain.info.CustomerSituation;
import com.mscreditevaluator.domain.info.DataClient;

import com.mscreditevaluator.domain.issuance.CardClient;
import com.mscreditevaluator.infra.clients.CardResourceClient;
import com.mscreditevaluator.infra.clients.ClientResourceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
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
            throw e; // ou trate de acordo com sua lógica de negócio
        }
    }
}

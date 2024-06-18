package com.mscreditevaluator.service;

import java.util.List;
import java.util.Optional;

import com.mscreditevaluator.domain.info.CustomerSituation;
import com.mscreditevaluator.domain.info.DataClient;
import com.mscreditevaluator.expection.erros.DataClientNotFoundExcption;
import com.mscreditevaluator.infra.clients.ClientResourceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CrediteValuatorService {

    private final ClientResourceClient clientResourceClient;

    public CustomerSituation getCustomerSituation(String idClient) {
        ResponseEntity<List<DataClient>> responseEntity = clientResourceClient.getClientById(idClient);
        validateResponse(responseEntity);

        DataClient dataClient = extractDataClient(responseEntity.getBody(), idClient);

        return CustomerSituation.builder()
                .client(dataClient)
                .build();
    }

    private void validateResponse(ResponseEntity<?> responseEntity) {
        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            throw new ResponseStatusException(HttpStatus.valueOf(responseEntity.getStatusCodeValue()),
                    "Erro ao acessar o serviço de cliente");
        }
        if (responseEntity.getBody() == null) {
            throw new DataClientNotFoundExcption("Cliente não encontrado");
        }
    }

    private DataClient extractDataClient(Object responseBody, String idClient) {
        // Respassa a lista de Clients
        if (responseBody instanceof List) {
            List<DataClient> dataList = (List<DataClient>) responseBody;

       // Filtra os clientes pelo Idclient passado como params
            Optional<DataClient> clientOptional = dataList.stream()
                    .filter(client -> idClient.equals(client.getIdClient()))
                    .findFirst();
    //Exepetions para garantir o funcionamento
            return clientOptional.orElseThrow(() ->
                    new DataClientNotFoundExcption("Cliente com id " + idClient + " não encontrado"));
        } else if (responseBody instanceof DataClient) {
            DataClient client = (DataClient) responseBody;
            if (!idClient.equals(client.getIdClient())) {
                throw new DataClientNotFoundExcption("Cliente com id " + idClient + " não encontrado");
            }
            return client;
        } else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Resposta inválida do servidor");
        }
    }
}

package com.mscreditevaluator.infra.clients;

import com.mscreditevaluator.domain.info.DataCard;
import com.mscreditevaluator.domain.info.DataClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



import java.util.List;
import java.util.UUID;

@FeignClient(value = "mscard", path = "/api/v1/card/")
public interface CardResourceClient {

    @GetMapping(params = "idClient")
    ResponseEntity<List<DataClient>> getCartoesByIdCliente(@RequestParam("idClient") UUID idClient);

    @GetMapping(value = "/less/income")
    ResponseEntity <List<DataCard>> getCardIncomeMax(@RequestParam("income") Long income);
}

package com.mscreditevaluator.infra.clients;

import com.mscreditevaluator.domain.info.CardClient;
import com.mscreditevaluator.domain.info.DataCard;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "mscard", url = "http://localhost:8082/mscard/api/v1/card")
public interface CardResourceClient {

    @GetMapping
    ResponseEntity<List<CardClient>> getCartoesByCliente(@RequestParam("idClient") String idClient);

    @GetMapping(value = "/less/income")
    ResponseEntity<List<DataCard>> getCardIncomeMax(@RequestParam("income") Long income);
}

package com.mscreditevaluator.infra.clients;

import com.mscreditevaluator.domain.info.CardClient;
import com.mscreditevaluator.domain.info.DataCard;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "mscard", url = "http://localhost:8082")
public interface CardResourceClient {

    @GetMapping("/api/v1/card")
    ResponseEntity<List<CardClient>> getCartoesByCliente(@RequestParam("idClient") String idClient);

    @GetMapping("/api/v1/card/less/income")
    ResponseEntity<List<DataCard>> getCardIncomeMax(@RequestParam("income") Long income);

}

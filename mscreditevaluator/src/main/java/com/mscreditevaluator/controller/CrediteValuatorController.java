package com.mscreditevaluator.controller;

import com.mscreditevaluator.domain.info.CustomerSituation;

import com.mscreditevaluator.infra.clients.ClientResourceClient;
import com.mscreditevaluator.service.CrediteValuatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/credite-valuator")
@RequiredArgsConstructor
public class CrediteValuatorController {

    private final CrediteValuatorService crediteValuatorService;
    private final ClientResourceClient clientResourceClient;

    @GetMapping(value = "/customer-situation", params = "idclient")
    public ResponseEntity<CustomerSituation> checkCustomerStatusClient(@RequestParam("idclient") String idclient) {
        CustomerSituation customerSituation = crediteValuatorService.getCustomerSituation(idclient);
        return ResponseEntity.ok(customerSituation);
    }
}

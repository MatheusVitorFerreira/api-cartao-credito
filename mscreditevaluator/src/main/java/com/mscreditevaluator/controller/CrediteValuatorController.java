package com.mscreditevaluator.controller;

import com.mscreditevaluator.domain.info.CustomerSituation;
import com.mscreditevaluator.service.CrediteValuatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/credite-valuator")
public class CrediteValuatorController {

    private final CrediteValuatorService crediteValuatorService;

    public CrediteValuatorController(CrediteValuatorService crediteValuatorService) {
        this.crediteValuatorService = crediteValuatorService;
    }

    @GetMapping(value = "/customer-situation", params = "idClient")
    public ResponseEntity<CustomerSituation> checkCustomerStatusClient(@RequestParam("idClient") String idClient) {
        CustomerSituation customerSituation = crediteValuatorService.getCustomerSituation(idClient);
        return ResponseEntity.ok(customerSituation);
    }
}

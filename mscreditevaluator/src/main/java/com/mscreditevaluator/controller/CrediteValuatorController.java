package com.mscreditevaluator.controller;

import com.mscreditevaluator.domain.info.CustomerSituation;
import com.mscreditevaluator.domain.info.DataEvaluation;
import com.mscreditevaluator.domain.info.ProtocolRequestCard;
import com.mscreditevaluator.domain.issuance.DataRequestCard;
import com.mscreditevaluator.domain.issuance.ReturnCustomerReview;
import com.mscreditevaluator.expection.erros.DataClientNotFoundExcption;
import com.mscreditevaluator.expection.erros.ErroRequestCardException;
import com.mscreditevaluator.expection.erros.ErrorCommunicationMicroservicesException;
import com.mscreditevaluator.service.CrediteValuatorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping()
    public ResponseEntity returnCustomerReview(@RequestBody DataEvaluation data) {
        try {
            ReturnCustomerReview returnCustomerReview = crediteValuatorService
                    .performAssessment(data.getIdClient(), data.getIncome());
            return ResponseEntity.ok(returnCustomerReview);
        } catch (DataClientNotFoundExcption e) {
            return ResponseEntity.notFound().build();
        } catch (ErrorCommunicationMicroservicesException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @PostMapping("request-card")
    public ResponseEntity<?> requestCard(@RequestBody DataRequestCard data) {
        try {
            ProtocolRequestCard protocolRequestCard = crediteValuatorService.requestCardIssuance(data);
            return ResponseEntity.ok(protocolRequestCard);
        } catch (ErroRequestCardException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}

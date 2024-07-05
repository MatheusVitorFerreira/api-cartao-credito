package edu.microservices.mscard.domain;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class DataRequestCard {
    private Long idCard;
    private String idClient;
    private BigDecimal limitApproved;
}


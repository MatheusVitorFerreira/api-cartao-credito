package com.mscreditevaluator.domain.issuance;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ApprovedCard {
    private String card;
    private String creditCardBrand;
    private BigDecimal limitApproved;
}
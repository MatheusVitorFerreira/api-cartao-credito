package com.mscreditevaluator.domain.issuance;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ApprovedCard {
    private String name;
    private String brand;
    private BigDecimal limitApproved;
}
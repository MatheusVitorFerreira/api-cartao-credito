package com.mscreditevaluator.domain.issuance;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CardClient {
    private String nameCard;
    private String creditCardBrand;
    private BigDecimal limitApproved ;
}

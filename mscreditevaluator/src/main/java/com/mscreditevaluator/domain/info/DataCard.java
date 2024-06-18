package com.mscreditevaluator.domain.info;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DataCard {
    private Long idCard;
    private String cardName;
    private String creditCardBrand;
    private BigDecimal minLimit;
}


package com.mscreditevaluator.domain.info;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CardClient {
    private String name;
    private String brand;
    private BigDecimal limitApproved ;
}

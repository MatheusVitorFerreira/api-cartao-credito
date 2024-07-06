package com.mscreditevaluator.domain.info;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataCard {
    private Long idCard;
    private String cardName;
    private String creditCardBrand;
    private BigDecimal minLimit;
}


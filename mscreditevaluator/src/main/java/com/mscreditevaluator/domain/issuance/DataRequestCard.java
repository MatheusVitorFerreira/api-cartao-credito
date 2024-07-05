package com.mscreditevaluator.domain.issuance;


import com.mscreditevaluator.domain.info.DataAddress;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class DataRequestCard {
    private Long idCard;
    private String idClient;
    private DataAddress address;
    private BigDecimal limitApproved;
}

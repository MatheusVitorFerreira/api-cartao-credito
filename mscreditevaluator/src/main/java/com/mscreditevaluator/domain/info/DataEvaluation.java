package com.mscreditevaluator.domain.info;

import lombok.Data;

import java.util.UUID;

@Data
public class DataEvaluation {
    private UUID idClient;
    private Long income;
}

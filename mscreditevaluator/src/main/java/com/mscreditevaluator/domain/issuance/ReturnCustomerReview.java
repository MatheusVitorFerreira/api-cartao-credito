package com.mscreditevaluator.domain.issuance;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ReturnCustomerReview {

    private List<ApprovedCard> cards;
}

package com.mscreditevaluator.domain.info;

import com.mscreditevaluator.domain.issuance.ClientCard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerSituation {
    private DataClient client;
    private List<ClientCard> cards;
}

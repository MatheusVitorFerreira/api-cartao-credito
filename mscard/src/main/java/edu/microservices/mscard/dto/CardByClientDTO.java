package edu.microservices.mscard.dto;

import edu.microservices.mscard.domain.CardClient;
import edu.microservices.mscard.domain.CreditCardBrand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CardByClientDTO {
    private String name;
    private String brand;
    private BigDecimal limitApproved;

    public CardByClientDTO(String cardName, CreditCardBrand creditCardBrand, BigDecimal limitApproved) {
    }

    public static CardByClientDTO fromModel(CardClient object) {
        return new CardByClientDTO(
                object.getCard().getCardName(),
                object.getCard().getCreditCardBrand().toString(),
                object.getLimitApproved()
        );
    }
}

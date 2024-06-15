package edu.microservices.mscard.dto;

import edu.microservices.mscard.domain.Card;
import edu.microservices.mscard.domain.CreditCardBrand;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class CardDTO {
    private Long idCard;
    private String cardName;
    private BigDecimal income;
    private CreditCardBrand creditCardBrand;
    private BigDecimal minLimit;

    public CardDTO() {
    }

    public CardDTO(Card card) {
        this.idCard = card.getIdCard();
        this.cardName = card.getCardName();
        this.income = card.getIncome();
        this.creditCardBrand = card.getCreditCardBrand();
        this.minLimit = card.getMinLimit();
    }

    public Card toModel() {
        return new Card(cardName, income, creditCardBrand, minLimit);
    }
}

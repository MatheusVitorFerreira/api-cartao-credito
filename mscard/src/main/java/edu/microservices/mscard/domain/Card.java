package edu.microservices.mscard.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Entity
public class Card implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCard;

    @Column
    private String cardName;

    @Column
    private BigDecimal income;

    @Column
    @Enumerated(EnumType.STRING)
    private CreditCardBrand creditCardBrand;

    @Column
    private BigDecimal minLimit;

    public Card(String cardName,
                BigDecimal income,
                CreditCardBrand creditCardBrand,
                BigDecimal minLimit) {
        this.cardName = cardName;
        this.income = income;
        this.creditCardBrand = creditCardBrand;
        this.minLimit = minLimit;
    }
}

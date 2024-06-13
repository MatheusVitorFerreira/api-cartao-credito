package edu.microservices.mscartao.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
public class Card implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idCard;

    @Column
    private  String cardName;

    @Column
    private BigDecimal income;

    @Column
    @Enumerated(EnumType.STRING)
    private CreditCardBrand creditCardBrand;

    @Column
    private BigDecimal minLimit;

    public Card(String cardName,
                BigDecimal income,
                BigDecimal minLimit) {

        this.cardName = cardName;
        this.income = income;
        this.minLimit = minLimit;
    }
}

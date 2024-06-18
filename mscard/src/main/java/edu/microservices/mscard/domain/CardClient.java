package edu.microservices.mscard.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Data
public class CardClient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCardClient;

    private String idClient;
    private String cpf;

    @ManyToOne
    @JoinColumn(name = "id_cartao")
    private Card card;
    private BigDecimal limitApproved;
}

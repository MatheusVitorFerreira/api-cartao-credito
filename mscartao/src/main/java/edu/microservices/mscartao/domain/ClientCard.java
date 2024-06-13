package edu.microservices.mscartao.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@NoArgsConstructor
@Data
public class ClientCard {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idClient;
}

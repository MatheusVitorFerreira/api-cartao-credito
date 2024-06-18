package edu.microservices.mscard.repository;

import edu.microservices.mscard.domain.CardClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientCardRepository extends JpaRepository<CardClient, Long> {
    List<CardClient> findByIdClient(String idClient);
}

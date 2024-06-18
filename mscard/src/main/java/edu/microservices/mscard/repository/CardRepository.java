package edu.microservices.mscard.repository;

import edu.microservices.mscard.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    boolean existsByCardName(String cardName);
    List<Card> findByIncomeLessThanEqual(BigDecimal income);
}


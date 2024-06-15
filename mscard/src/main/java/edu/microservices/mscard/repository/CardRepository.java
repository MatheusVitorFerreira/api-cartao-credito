package edu.microservices.mscard.repository;

import edu.microservices.mscard.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    boolean existsByCardName(String cardName);

    boolean existsByCardNameAndIdCardNot(String cardName, Long idCard);
}

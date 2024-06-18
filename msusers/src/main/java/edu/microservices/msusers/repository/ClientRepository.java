package edu.microservices.msusers.repository;

import edu.microservices.msusers.domain.Client;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ClientRepository extends MongoRepository<Client, String> {
    boolean existsByFullName(String fullName);
}
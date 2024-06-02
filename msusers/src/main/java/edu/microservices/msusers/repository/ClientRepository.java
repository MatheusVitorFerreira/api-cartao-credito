package edu.microservices.msusers.repository;

import edu.microservices.msusers.domain.Client;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.rmi.server.UID;

@Repository
public interface ClientRepository extends MongoRepository<Client, String> {
    boolean existsByFullName(String fullName);
    boolean existsByFullNameAndIdNot(String fullName, String id);
}

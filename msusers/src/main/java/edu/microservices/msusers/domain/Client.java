package edu.microservices.msusers.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "clients")
public class Client extends Users {

    @Id
    private String idClient;

    @NotBlank
    private double patrimony;

    @DBRef
    private List<Address> addresses;
}

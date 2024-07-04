package edu.microservices.msusers.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
@NoArgsConstructor
@Document(collection = "addresses")
public class Address {

    @Id
    private String id;
    private String street;
    private String city;
    private String state;
    private String country;

    public Address(String city, String street, String state, String country) {
        this.city = city;
        this.street = street;
        this.state = state;
        this.country = country;
    }
}

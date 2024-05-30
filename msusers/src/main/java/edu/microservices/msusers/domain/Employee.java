package edu.microservices.msusers.domain;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "employees")
public class Employee extends Users {

    @Id
    private String id;
    private String position;
    private double salary;
    private String department;

    @DBRef
    private List<Address> addresses;

}

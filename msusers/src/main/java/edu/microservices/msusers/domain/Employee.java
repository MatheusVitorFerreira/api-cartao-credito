package edu.microservices.msusers.domain;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    private String idEmployees;


    private String position;

    private double salary;

    private String department;

    private TypeEmployee typeEmployee;

    @DBRef
    private List<Address> addresses;

}

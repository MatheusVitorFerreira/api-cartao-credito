package edu.microservices.msusers.domain;

import jakarta.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "employees")
public class Employee extends Users {

    @Id
    private String idEmployee; // Aqui deve ser idEmployee, não idEmployees

    private String position;

    private double salary;

    private String department;

    private TypeEmployee typeEmployee;

    @DBRef
    private Address address;

}

package edu.microservices.msusers.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public abstract class Users {

    private String fullName;
    private String password;
    private int age;
    private String phoneNumber;
    private String cpf;
}

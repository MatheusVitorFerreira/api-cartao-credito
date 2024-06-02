package edu.microservices.msusers.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.microservices.msusers.domain.Address;
import edu.microservices.msusers.domain.Client;
import lombok.Data;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ClientDTO {

    private String fullName;
    private String password;
    private int age;
    private String phoneNumber;
    private String id;
    private double patrimony;
    private List<Address> addresses;

    public ClientDTO() {
    }

    public ClientDTO(Client client, List<Address> addresses) {
        this.fullName = client.getFullName();
        this.age = client.getAge();
        this.password = client.getPassword();
        this.phoneNumber = client.getPhoneNumber();
        this.id = client.getId();
        this.patrimony = client.getPatrimony();
        this.addresses = addresses;
    }
    public Client toClient(){
        Client client = new Client();
        client.setFullName(this.fullName);
        client.setAge(this.age);
        client.setPassword(this.password);
        client.setPhoneNumber(this.phoneNumber);
        client.setId(this.id);
        client.setPatrimony(this.patrimony);
        return client;
    }
}

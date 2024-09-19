package edu.microservices.msusers.service;

import edu.microservices.msusers.domain.Address;
import edu.microservices.msusers.domain.Client;
import edu.microservices.msusers.dto.ClientDTO;
import edu.microservices.msusers.exception.erros.ClientNotFoundException;
import edu.microservices.msusers.exception.erros.DuplicateClientException;
import edu.microservices.msusers.exception.erros.EmployeeNotFoundException;
import edu.microservices.msusers.repository.AddressRepository;
import edu.microservices.msusers.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AddressRepository addressRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<ClientDTO> findAll() {
        return clientRepository.findAll().stream()
                .map(client -> {
                    Address address = client.getAddress();
                    return new ClientDTO(client, address);
                })
                .collect(Collectors.toList());
    }

    public ClientDTO findById(String id) {
        Optional<Client> clientOptional = clientRepository.findById(id);
        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            Address address = client.getAddress();
            if (address == null) {
                throw new ClientNotFoundException("Address not found for client with id " + id);
            }
            return new ClientDTO(client, address);
        } else {
            throw new ClientNotFoundException("Client not found with id " + id);
        }
    }

    public ClientDTO save(ClientDTO clientDTO) {
        String name = clientDTO.getFullName();
        if (clientRepository.existsByFullName(name)) {
            throw new DuplicateClientException("Client with name " + name + " already exists");
        }
        String encryptedPassword = encryptPassword(clientDTO.getPassword());

        Address address = clientDTO.getAddress();
        Address savedAddress = addressRepository.save(address);

        Client client = clientDTO.toClient();
        client.setPassword(encryptedPassword);
        client.setAddress(savedAddress);

        Client savedClient = clientRepository.save(client);

        return new ClientDTO(savedClient, savedAddress);
    }


    public ClientDTO update(String id, ClientDTO object) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (optionalClient.isPresent()) {
            String name = object.getFullName();
            if (clientRepository.existsByFullName(name)) {
                throw new DuplicateClientException("Another client with name" + name + " already exists");
            }
            Client client = optionalClient.get();
            client.setAge(object.getAge());
            client.setPassword(object.getPassword());
            client.setPatrimony(object.getPatrimony());
            client.setPhoneNumber(object.getPhoneNumber());
            client.setIncome(object.getIncome());

            Address address = object.getAddress();
            if (address != null) {
                client.setAddress(address);
            }

            Client updatedClient = clientRepository.save(client);
            return new ClientDTO(updatedClient, address);
        } else {
            throw new ClientNotFoundException("Client not found with id " + id);
        }
    }

    public void delete(String id) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();
            clientRepository.delete(client);
        } else {
            throw new EmployeeNotFoundException("Client not found with id " + id);
        }
    }

    private String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }
}

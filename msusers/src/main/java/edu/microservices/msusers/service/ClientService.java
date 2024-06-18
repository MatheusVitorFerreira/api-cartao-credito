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
        return clientRepository.findAll().stream().map(client -> {
            List<Address> addresses = addressRepository
                    .findAllById(client
                            .getAddresses()
                            .stream()
                            .map(Address::getId)
                            .collect(Collectors.toList()));
            return new ClientDTO(client, addresses);
        }).collect(Collectors.toList());
    }

    public ClientDTO findById(String id) {
        Optional<Client> client = clientRepository.findById(id);
        if (client.isPresent()) {
            List<Address> addresses = addressRepository.findAllById(client.get()
                    .getAddresses()
                    .stream()
                    .map(Address::getId)
                    .collect(Collectors.toList()));
            return new ClientDTO(client.get(), addresses);
        } else {
            throw new ClientNotFoundException("Client not found with id " + id);
        }
    }

    public ClientDTO save(ClientDTO clientDTO) {
        String name = clientDTO.getFullName();
        if (clientRepository.existsByFullName(name)) {
            throw new DuplicateClientException("Client" +
                    " with name " + name + " already exists");
        }
        String encryptedPassword = encryptPassword(clientDTO.getPassword());
        List<Address> addresses = clientDTO.getAddresses();
        if (addresses != null) {
            addresses = addresses.stream().map(addressRepository::save).collect(Collectors.toList());
        }
        Client client = clientDTO.toClient();
        client.setPassword(encryptedPassword);
        client.setAddresses(addresses);
        Client saveClient = clientRepository.save(client);
        return new ClientDTO(saveClient, addresses);
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

            List<Address> addresses = object.getAddresses();
            if (addresses != null) {
                addresses = addresses.stream().map(addressRepository::save).collect(Collectors.toList());
                client.setAddresses(addresses);
            }
            Client updateClient = clientRepository.save(client);
            return new ClientDTO(updateClient, addresses);
        } else {
            throw new ClientNotFoundException("Client not found with id " + id);
        }
    }

    public void delete(String id) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();
            List<Address> addresses = client.getAddresses();
            if (addresses != null && !addresses.isEmpty()) {
                for (Address address : addresses) {
                    addressRepository.delete(address);
                }
            }
            clientRepository.deleteById(id);
        } else {
            throw new EmployeeNotFoundException("Client not found with id " + id);
        }
    }

    private String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }
}

package edu.microservices.msusers.controller;

import edu.microservices.msusers.dto.ClientDTO;
import edu.microservices.msusers.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/client")
@RequiredArgsConstructor
@Slf4j
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        List<ClientDTO> clients = clientService.findAll();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/get-id")
    public ResponseEntity<ClientDTO> getClientById(@RequestParam("idClient") String id) {
        ClientDTO clientDTO = clientService.findById(id);
        return ResponseEntity.ok(clientDTO);
    }
    @PostMapping()
    public ResponseEntity<Void> createClient(@Valid @RequestBody ClientDTO clientDTO) {
        ClientDTO savedClientDTO = clientService.save(clientDTO);

        URI headerLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{idClient}")
                .buildAndExpand(savedClientDTO.getIdClient())
                .toUri();
        return ResponseEntity.created(headerLocation).build();
    }

    @PutMapping("/{idClient}")
    public ResponseEntity<ClientDTO> updateClient(@PathVariable String id, @Valid @RequestBody ClientDTO clientDTO) {
        ClientDTO updatedClient = clientService.update(id, clientDTO);
        return ResponseEntity.ok(updatedClient);
    }

    @DeleteMapping("/{idClient}")
    public ResponseEntity<Void> deleteClient(@PathVariable String id) {
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
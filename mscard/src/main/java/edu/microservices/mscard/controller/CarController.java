package edu.microservices.mscard.controller;


import edu.microservices.mscard.dto.CardDTO;
import edu.microservices.mscard.service.CardService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/card")
public class CarController {

    @Autowired
    private CardService cardService;

    @GetMapping
    public ResponseEntity<List<CardDTO>> getAllCards(){
        List<CardDTO> cards = cardService.findAll();
        return ResponseEntity.ok(cards);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity <CardDTO> getCardById(@PathVariable Long id){
        CardDTO  card = cardService.findById(id);
        return ResponseEntity.ok(card);
    }

    @PostMapping
    public ResponseEntity<CardDTO> save(@RequestBody CardDTO cardDTO) {
        CardDTO savedCard = cardService.saveCard(cardDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCard);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity <CardDTO> updateCard(@PathVariable Long id, @Valid @RequestBody CardDTO object) {
        CardDTO  updateCard = cardService.update(id,object);
        return ResponseEntity.ok(updateCard);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCard(@PathVariable Long id) {
        cardService.deleteCard(id);
        return ResponseEntity.noContent().build();
    }
}

package edu.microservices.mscard.service;

import edu.microservices.mscard.domain.CardClient;
import edu.microservices.mscard.repository.ClientCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardClientService {

    @Autowired
    private ClientCardRepository ClienteCartaoRepository;

    public List<CardClient> ListCardId(String idClient){
        return ClienteCartaoRepository.findByIdClient(idClient);
    }
}

package com.mscreditevaluator.infra.mqueue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mscreditevaluator.domain.issuance.DataRequestCard;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PublisherCardIssuanceRequest {

    private final RabbitTemplate rabbitTemplate;
    private final Queue queueCardIssuance;
    private final ObjectMapper objectMapper;

    public void requestCard(DataRequestCard data) throws JsonProcessingException {
        var json = convertIntoJson(data);
        rabbitTemplate.convertAndSend(queueCardIssuance.getName(),json);
    }
    private String convertIntoJson(DataRequestCard data) throws JsonProcessingException {
        return objectMapper.writeValueAsString(data);
    }
}


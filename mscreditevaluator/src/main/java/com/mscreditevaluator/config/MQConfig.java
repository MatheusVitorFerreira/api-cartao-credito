package com.mscreditevaluator.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

    @Value("${mq.queues.card-issuance}")
    private String queueCardEmissions;

    @Bean
    public Queue queueIssCardEmissions(){
        return new Queue(queueCardEmissions,true);
    }
}

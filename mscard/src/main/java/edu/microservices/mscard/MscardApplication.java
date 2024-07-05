package edu.microservices.mscard;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class MscardApplication {

    public static void main(String[] args) {
        SpringApplication.run(MscardApplication.class, args);
    }

}

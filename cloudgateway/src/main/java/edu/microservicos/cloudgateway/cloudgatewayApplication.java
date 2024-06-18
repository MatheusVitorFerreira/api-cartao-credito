package edu.microservicos.cloudgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class cloudgatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(cloudgatewayApplication.class, args);
    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route(r -> r.path("/api/v1/employees/**").uri("lb://msusers"))
                .route(r -> r.path("/api/v1/client/**").uri("lb://msusers"))
                .route(r -> r.path("/api/v1/card/**").uri("lb://mscard"))
                .route(r -> r.path("/api/v1/credite-valuator/**").uri("lb://mscreditevaluator"))
                .build();
    }
}
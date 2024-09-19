package edu.microservices.msusers.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Microserviço de Usúarios")
                        .version("v1")
                        .description("Sistema de criação de clientes REST API")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://springdoc.org"))
                )
                .servers(Collections.singletonList(new Server().url("/")));
    }
}

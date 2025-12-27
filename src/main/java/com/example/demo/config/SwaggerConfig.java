package com.example.demo.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.*;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info().title("Inventory Balancer API").version("1.0"))
                // FIX: Explicitly set the Server URL so Swagger knows where to send requests
                // Make sure to use 'https' to avoid Mixed Content errors in the browser
                .servers(List.of(new Server().url("https://9220.408procr.amypo.ai/"))) 
                .addSecurityItem(new SecurityRequirement().addList("Bearer Auth"))
                .components(new Components().addSecuritySchemes("Bearer Auth",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
    }
}
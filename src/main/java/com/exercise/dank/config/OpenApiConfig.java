package com.exercise.dank.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Piotr Kusibab",
                        email = "piotr.ka94@gmail.com"
                ),
                description = "OpenApi documentation for Spring App",
                title = "OpenApi specification - Piotr Kusibab",
                version = "1.0",
                license = @License(
                        name = "Licence name",
                        url = "https://github.com/KusibabPiotr"
                ),
                termsOfService = "Terms of service"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080"
                )
        }
)
public class OpenApiConfig {
}

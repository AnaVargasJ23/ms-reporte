package com.onclass.reporte.infrastructure.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "MS Reporte - On Class",
        version = "1.0",
        description = "Microservicio de reportes y métricas"
    )
)
public class OpenApiConfig {
}

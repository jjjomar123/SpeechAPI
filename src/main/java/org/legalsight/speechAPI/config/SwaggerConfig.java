package org.legalsight.speechAPI.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Speech API",
                version = "Verions 1.0",
                description = "LegalSight Speech API"
        )
)
public class SwaggerConfig {
}

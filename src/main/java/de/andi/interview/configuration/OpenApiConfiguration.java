package de.andi.interview.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(info = @Info(title = "GitHub Proxy API", version = "2.0", description = "Retrieves Github repositories for certain criteria"))
@Configuration
public class OpenApiConfiguration {
}

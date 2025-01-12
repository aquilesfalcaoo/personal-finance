package com.personal.finance.infrastructure.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Personal Finance API")
                        .version("1.0")
                        .description("Personal finance API puts you in control of your spending. Track transactions, set budgets, and add to savings pots easily."));
    }
}

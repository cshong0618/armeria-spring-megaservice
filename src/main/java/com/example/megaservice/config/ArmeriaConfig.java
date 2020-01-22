package com.example.megaservice.config;

import com.example.megaservice.service.BasicHttpService;
import com.linecorp.armeria.server.docs.DocService;
import com.linecorp.armeria.server.logging.LoggingService;
import com.linecorp.armeria.spring.ArmeriaServerConfigurator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ArmeriaConfig {
    @Bean
    public ArmeriaServerConfigurator armeriaServerConfigurator(
            BasicHttpService basicHttpService
    ) {
        return serverBuilder -> {
            serverBuilder
                    .serviceUnder("/docs", new DocService())
                    .annotatedService(basicHttpService)
                    .decorator(LoggingService.newDecorator())
            ;
        };
    }
}

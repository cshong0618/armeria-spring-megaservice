package com.example.megaservice.config;

import com.example.megaservice.service.BasicGrpcService;
import com.example.megaservice.service.BasicHttpService;
import com.example.megaservice.service.JsonPlaceholderService;
import com.linecorp.armeria.client.ClientOptions;
import com.linecorp.armeria.client.retrofit2.ArmeriaRetrofitBuilder;
import com.linecorp.armeria.common.grpc.GrpcSerializationFormats;
import com.linecorp.armeria.server.docs.DocService;
import com.linecorp.armeria.server.grpc.GrpcService;
import com.linecorp.armeria.server.logging.LoggingService;
import com.linecorp.armeria.spring.ArmeriaServerConfigurator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.time.Duration;

@Configuration
public class ArmeriaConfig {
    @Bean
    public JsonPlaceholderService jsonPlaceholderService() {
        Retrofit retrofit = new ArmeriaRetrofitBuilder()
                .baseUrl("http://jsonplaceholder.typicode.com")
                .clientOptions(ClientOptions.builder()
                        .writeTimeout(Duration.ofSeconds(3))
                        .responseTimeout(Duration.ofSeconds(3))
                        .build())
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        JsonPlaceholderService service = retrofit.create(JsonPlaceholderService.class);
        return service;
    }

    @Bean
    public ArmeriaServerConfigurator armeriaServerConfigurator(
            BasicHttpService basicHttpService,
            BasicGrpcService basicGrpcService
    ) {
        return serverBuilder -> {
            serverBuilder
                    .serviceUnder("/docs", new DocService())
                    .service(GrpcService.builder()
                            .addService(basicGrpcService)
                            .supportedSerializationFormats(GrpcSerializationFormats.values())
                            .enableUnframedRequests(true)
                            .build()
                    )
                    .annotatedService(basicHttpService)
                    .decorator(LoggingService.newDecorator())
            ;
        };
    }
}

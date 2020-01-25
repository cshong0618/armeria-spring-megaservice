package com.example.megaservice.service;

import com.linecorp.armeria.client.WebClient;
import com.linecorp.armeria.common.AggregatedHttpResponse;
import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.Param;
import com.linecorp.armeria.server.annotation.Post;
import com.linecorp.armeria.server.annotation.ProducesJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import retrofit2.Response;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
public class BasicHttpService {
    private Logger logger = LoggerFactory.getLogger(BasicHttpService.class);
    private JsonPlaceholderService jsonPlaceholderService;

    public BasicHttpService(JsonPlaceholderService jsonPlaceholderService) {
        this.jsonPlaceholderService = jsonPlaceholderService;
    }

    @Get("/index")
    public String index() {
        return "This is index";
    }

    @Get("/name/{name}")
    public String greetName(@Param("name") String name) {
        return "Hello " + name;
    }

    @Get("/user/{name}")
    @ProducesJson
    public User getUserWithName(@Param("name") String name) {
        User user = new User();
        user.setName(name);

        return user;
    }

    @Post("/user")
    public String extractNameFromUser(User user) {
        return Optional.ofNullable(user)
                .map(User::getName)
                .orElse("No name");
    }

    @Post("/reverse")
    @ProducesJson
    public User reverseName(User user) throws Exception {
        return Optional.ofNullable(user)
                .map(User::getName)
                .map(n -> new StringBuilder(n)
                        .reverse()
                        .toString())
                .map(n -> {
                    User user1 = new User();
                    user1.setName(n);

                    return user1;
                })
                .orElse(null);
    }

    @Get("/external")
    @ProducesJson
    public JsonPlaceholder externalCall() {
        try {
            return jsonPlaceholderService.getTodo().get(6, TimeUnit.SECONDS);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

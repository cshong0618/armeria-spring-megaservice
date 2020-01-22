package com.example.megaservice.service;

import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.Param;
import com.linecorp.armeria.server.annotation.Post;
import com.linecorp.armeria.server.annotation.ProducesJson;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BasicHttpService {
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
    public User reverseName(User user) throws Exception{
        String name = Optional.ofNullable(user)
                .map(User::getName)
                .map(n -> new StringBuilder(n)
                .reverse()
                .toString())
                .orElse("");

        user.setName(name);

        return user;
    }
}

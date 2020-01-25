package com.example.megaservice.service;

import retrofit2.Call;
import retrofit2.http.GET;

import java.util.concurrent.CompletableFuture;

public interface JsonPlaceholderService {
    @GET("todos/1")
    CompletableFuture<JsonPlaceholder> getTodo();
}

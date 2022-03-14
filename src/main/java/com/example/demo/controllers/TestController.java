package com.example.demo.controllers;

import com.example.demo.configuration.TestProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@RestController
@RequiredArgsConstructor
public class TestController {

//    final public Object bean;
    final public TestProperties properties;

    public CompletableFuture<String> getObject() {
        CompletableFuture<String> c = CompletableFuture.supplyAsync(() -> {
            return "String";
        });

        return c.thenApply(String::toLowerCase);
    }
}

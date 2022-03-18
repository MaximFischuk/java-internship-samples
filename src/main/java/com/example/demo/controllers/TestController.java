package com.example.demo.controllers;

import com.example.demo.service.BasicMessageProducer;
import com.example.demo.stream.BasicMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final BasicMessageProducer producer;

    @PostMapping("/submit")
    public CompletableFuture<Boolean> submitMessage(@RequestBody BasicMessage message) {
        return CompletableFuture.supplyAsync(() -> {
            producer.produce(message);
            return true;
        });
    }

    public CompletableFuture<String> getObject() {
        CompletableFuture<String> c = CompletableFuture.supplyAsync(() -> {
            return "String";
        });

        return c.thenApply(String::toLowerCase);
    }
}

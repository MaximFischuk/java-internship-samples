package com.example.demo.service;

import com.example.demo.stream.BasicMessage;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Supplier;

@Slf4j
public class KafkaMQService implements Supplier<Flux<BasicMessage>>, BasicMessageProducer {
    private final Sinks.Many<BasicMessage> sink = Sinks.many().unicast().onBackpressureBuffer();

    @Override
    public void produce(BasicMessage message) {
        sink.emitNext(message, Sinks.EmitFailureHandler.FAIL_FAST);
    }

    @Override
    public Flux<BasicMessage> get() {
        return sink.asFlux();
    }
}

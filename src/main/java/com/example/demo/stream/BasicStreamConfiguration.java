package com.example.demo.stream;

import com.example.demo.service.BasicMessageProducer;
import com.example.demo.service.KafkaMQService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;
import java.util.function.Function;

@Configuration
@Slf4j
public class BasicStreamConfiguration {

    @Bean("basic-producer")
    public BasicMessageProducer basicProducer(){
        return new KafkaMQService();
    }

    @Bean("basic-processor")
    public Function<Flux<Integer>, Flux<String>> basicProcessor(){
        return longFlux -> longFlux
                .map(Object::toString)
                .log();
    }

    @Bean("basic-consumer")
    public Consumer<BasicMessage> basicConsumer(){
        return (value) -> log.info("Consumer Received : " + value);
    }

}

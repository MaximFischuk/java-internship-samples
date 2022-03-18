package com.example.demo.service;

import lombok.extern.slf4j.Slf4j;
import me.tongfei.progressbar.DelegatingProgressBarConsumer;
import me.tongfei.progressbar.ProgressBarBuilder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@Service
@Slf4j
public class ReactiveService {

    public Flux<Integer> makeSimpleFlux(int limit) {
        return Flux.fromStream(IntStream.rangeClosed(0, limit).boxed())
                .delayElements(Duration.ofMillis(50))
                .doOnNext(this::print)
                .doOnError(this::error)
                .doOnTerminate(() -> log.info("Flow finished"));
    }

    public Mono<Integer> makeSimpleMono() {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return 42;
                }))
                .doOnNext(this::print)
                .doOnError(this::error);
    }

    private void print(Integer value) {
        log.info("Receive value: {}", value);
    }

    private void error(Throwable throwable) {
        log.error("Error on execution", throwable);
    }

    public static void main(String[] args) {
        final var amount = 1000;
        final var service = new ReactiveService();
        final var atomic = new AtomicInteger(0);

        final var progressBar = new ProgressBarBuilder()
                .setInitialMax(amount)
                .setTaskName("Flux")
                .setConsumer(new DelegatingProgressBarConsumer(log::info))
                .continuousUpdate()
                .build();

        final var flux = service.makeSimpleFlux(amount);
        final var mono = service.makeSimpleMono();

        flux.subscribe(atomic::set);
        mono.subscribe((value) -> log.info("Mono value: " + value));

        do {
            progressBar.stepTo(atomic.get());
        } while (atomic.get() != amount);

        progressBar.close();

    }

}

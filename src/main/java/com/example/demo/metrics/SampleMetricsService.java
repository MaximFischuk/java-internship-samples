package com.example.demo.metrics;

import io.micrometer.core.instrument.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class SampleMetricsService {

    private static final String SAMPLE_COUNTER = "demo.sample.counter";
    private static final String SAMPLE_TIMER = "demo.sample.timer";
    private static final String SAMPLE_GAUGE = "demo.sample.gauge";

    private final Counter counter;
    private final Timer timer;
    private final Gauge gauge;

    private final AtomicLong summary = new AtomicLong(123);

    public SampleMetricsService(MeterRegistry meterRegistry) {
        this.counter = Counter.builder(SAMPLE_COUNTER).description("Some text").tag("demo", "some-tag1").register(meterRegistry);
        this.timer = Timer.builder(SAMPLE_TIMER).tag("demo", "some-tag2").publishPercentileHistogram().register(meterRegistry);
        this.gauge = Gauge.builder(SAMPLE_GAUGE, this.summary::get).tag("demo", "some-tag3").strongReference(true).register(meterRegistry);

        this.runWorker();
    }

    public void increment() {
        this.counter.increment();
    }

    @SneakyThrows
    public <T> T executionTime(Callable<T> extract) {
        return this.timer.recordCallable(extract);
    }

    public void setValue(Long value) {
        this.summary.set(value);
    }

    private void runWorker() {
        Flux.range(42, 500000)
                .map(Integer::longValue)
                .buffer(100)
                .delayElements(Duration.ofMillis(500))
                .doOnError((error) -> log.error("Some error in stream", error))
                .doOnNext((_ignored) -> increment())
                .subscribe((data) -> {
                    var result = executionTime(() -> data.stream().reduce(1L, (a, b) -> a * b));
                    setValue(result);
                });
    }

}

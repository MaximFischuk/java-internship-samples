package com.example.demo.beans;

import com.example.demo.configuration.TestProperties;
import com.example.demo.metrics.SampleMetricsService;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Beans {

    public TestProperties properties;

    @Bean
    public Object createBean() {
        return null;
    }

    @Bean
    public SampleMetricsService metricsService(MeterRegistry meterRegistry) {
        return new SampleMetricsService(meterRegistry);
    }

    @Bean
    public TimedAspect timedAspect(MeterRegistry registry) {
        return new TimedAspect(registry);
    }

}

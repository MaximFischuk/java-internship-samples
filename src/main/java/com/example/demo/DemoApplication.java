package com.example.demo;

import com.example.demo.configuration.TestProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
@RequiredArgsConstructor
public class DemoApplication {

    private final TestProperties properties;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}

package com.example.demo.beans;

import com.example.demo.configuration.TestProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Beans {

    public TestProperties properties;

    @Bean
    public Object createBean() {
        return null;
    }

}

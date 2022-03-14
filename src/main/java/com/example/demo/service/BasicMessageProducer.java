package com.example.demo.service;

import com.example.demo.stream.BasicMessage;

public interface BasicMessageProducer {

    void produce(BasicMessage message);

}

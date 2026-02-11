package com.fhirprocessor.handler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KafkaBootstrapLogger {

    public KafkaBootstrapLogger(@Value("${kafka.bootstrap-servers}") String bs) {
        System.out.println("Kafka bootstrap-servers = " + bs);
    }
}
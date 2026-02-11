package com.fhirprocessor.consumer;

import com.fhirprocessor.handler.FhirTransactionProcessor;
import org.apache.pekko.Done;
import org.apache.pekko.actor.typed.ActorRef;
import org.apache.pekko.actor.typed.ActorSystem;
import org.apache.pekko.actor.typed.Scheduler;
import org.apache.pekko.actor.typed.javadsl.AskPattern;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.CompletionStage;

@Component
public class FhirTransactionConsumer {
    private final ActorRef<FhirTransactionProcessor.Commnad> processor;
    private final Scheduler scheduler;
    private final Duration askTimeout = Duration.ofSeconds(10);

    public FhirTransactionConsumer(ActorRef<FhirTransactionProcessor.Commnad> processor, ActorSystem<?> system) {
        this.processor = processor;
        this.scheduler = system.scheduler();
    }

    @KafkaListener(
            id = "fhir-tx-consumer",
            topics = "${kafka.topic}",
            groupId = "${kafka.consumer}"
    )
    public void consume(String payload, Acknowledgment ack) {
        CompletionStage<Done> stage = AskPattern.<FhirTransactionProcessor.Commnad, Done>ask(
                processor,
                replyTo -> new FhirTransactionProcessor.Handle(payload, replyTo),
                askTimeout,
                scheduler
        );
        stage.whenComplete((ok, ex) -> {
            if (ex != null) {
                System.err.println("Erro no ator: " + ex.getMessage());
            } else {
                System.out.println("Recebi do Kafka: " + payload);
                ack.acknowledge();
            }
        });
    }
}

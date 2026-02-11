package com.fhirprocessor.config;

import com.fhirprocessor.handler.FhirTransactionProcessor;
import org.apache.pekko.actor.typed.ActorRef;
import org.apache.pekko.actor.typed.ActorSystem;
import org.apache.pekko.actor.typed.Behavior;
import org.apache.pekko.actor.typed.Props;
import org.apache.pekko.actor.typed.javadsl.Behaviors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PekkoConfig {

    @Bean
    public ActorSystem<Void> actorSystem(){
        return ActorSystem.create(Behaviors.empty(), "fhir-processor-system");
    }

    @Bean
    public ActorRef<FhirTransactionProcessor.Commnad> fhirTransactionProcessor(ActorSystem<Void>system){
        return system.systemActorOf(FhirTransactionProcessor.create(), "fhir-transaction-processor", Props.empty());

    }
}

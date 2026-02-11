package com.fhirprocessor.handler;

import org.apache.pekko.Done;
import org.apache.pekko.actor.typed.ActorRef;
import org.apache.pekko.actor.typed.Behavior;
import org.apache.pekko.actor.typed.javadsl.AbstractBehavior;
import org.apache.pekko.actor.typed.javadsl.ActorContext;
import org.apache.pekko.actor.typed.javadsl.Behaviors;
import org.apache.pekko.actor.typed.javadsl.Receive;

public class FhirTransactionProcessor extends AbstractBehavior<FhirTransactionProcessor.Commnad> {
    public interface Commnad {}

    public static final class Handle implements Commnad {
        public final String payload;
        public final ActorRef<Done> replyTo;

        public Handle(String payload, ActorRef<Done> replyTo) {
            this.payload = payload;
            this.replyTo = replyTo;
        }
    }

    public static Behavior<Commnad> create() {
        return Behaviors.setup(FhirTransactionProcessor::new);
    }

    private FhirTransactionProcessor(ActorContext<Commnad> ctx) {
        super(ctx);
    }

    @Override
    public Receive<Commnad> createReceive() {
        return newReceiveBuilder()
                .onMessage(Handle.class, this::onHandle)
                .build();
    }


    private Behavior<Commnad> onHandle(Handle msg) {
        // aqui fazes a validação e o processamento
        msg.replyTo.tell(Done.getInstance());
        return this;
    }


}

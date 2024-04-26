package com.hexi.Cerberus.adapter.messaging;

import com.hexi.Cerberus.infrastructure.messaging.Message;
import com.hexi.Cerberus.infrastructure.messaging.MessagePublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@Slf4j
public class MessagePublisherStub implements MessagePublisher {

    @Override
    public void publish(Collection<? extends Message> messages) {
        for (Message m : messages)
        log.info(m.toString());
    }
}

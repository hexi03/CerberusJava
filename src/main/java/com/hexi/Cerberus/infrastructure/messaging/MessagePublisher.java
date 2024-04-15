package com.hexi.Cerberus.infrastructure.messaging;

import com.hexi.Cerberus.infrastructure.event.DomainEvent;

import java.util.Collection;
import java.util.List;

public interface MessagePublisher {
    void publish(Collection<? extends Message> messages);
}

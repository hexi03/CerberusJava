package com.hexi.Cerberus.infrastructure.messaging;

import com.hexi.Cerberus.infrastructure.event.DomainEvent;

import java.util.List;

public interface MessagePublisher {
    void publish(List<? extends Message> messages);
}

package com.hexi.Cerberus.infrastructure.aggregate;

import com.hexi.Cerberus.infrastructure.event.DomainEvent;

import java.util.List;

public interface AggregateRoot {
    void clearEvents();

    List<DomainEvent> edjectEvents();
}
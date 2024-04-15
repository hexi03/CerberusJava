package com.hexi.Cerberus.infrastructure.aggregate;

import com.hexi.Cerberus.infrastructure.event.DomainEvent;

import java.util.Collection;
import java.util.List;

public interface AggregateRoot {
    void clearEvents();

    Collection<DomainEvent> edjectEvents();
}
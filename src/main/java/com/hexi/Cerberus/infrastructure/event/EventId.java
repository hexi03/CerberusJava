package com.hexi.Cerberus.infrastructure.event;

import lombok.Data;

import java.util.UUID;

@Data
public class EventId {
    public final UUID id;
    public static EventId generate() {
        return new EventId(UUID.randomUUID());
    }
}

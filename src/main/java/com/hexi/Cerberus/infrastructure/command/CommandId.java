package com.hexi.Cerberus.infrastructure.command;

import lombok.Data;

import java.util.UUID;

@Data
public class CommandId {
    public final UUID id;

    public static CommandId generate() {
        return new CommandId(UUID.randomUUID());
    }
}
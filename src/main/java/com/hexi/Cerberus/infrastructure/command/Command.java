package com.hexi.Cerberus.infrastructure.command;


import com.hexi.Cerberus.infrastructure.ValidationResult;

public interface Command {
    CommandId getId();

    ValidationResult validate();
}

package com.hexi.Cerberus.domain.report.command.create;

import com.hexi.Cerberus.infrastructure.command.Command;
import com.hexi.Cerberus.infrastructure.command.CommandId;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public abstract class CreateReportCmd implements Command {
    CommandId id;

    @Override
    public CommandId getId() {
        return id;
    }
}

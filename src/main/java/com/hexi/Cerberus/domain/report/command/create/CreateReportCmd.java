package com.hexi.Cerberus.domain.report.command.create;

import com.hexi.Cerberus.domain.user.UserID;
import com.hexi.Cerberus.infrastructure.ValidationResult;
import com.hexi.Cerberus.infrastructure.command.Command;
import com.hexi.Cerberus.infrastructure.command.CommandId;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@SuperBuilder
@Getter
public abstract class CreateReportCmd implements Command {
    CommandId id;
    UserID creatorId;

    @Override
    public CommandId getId() {
        return id;
    }

    public ValidationResult validate(List<String> problems) {
        if(id == null) problems.add("Command id is null");
        if(creatorId == null) problems.add("Creator id is null");
        return new ValidationResult(problems);
    }
}

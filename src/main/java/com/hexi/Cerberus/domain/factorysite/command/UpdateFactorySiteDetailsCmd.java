package com.hexi.Cerberus.domain.factorysite.command;

import com.hexi.Cerberus.domain.factorysite.FactorySiteID;
import com.hexi.Cerberus.infrastructure.ValidationResult;
import com.hexi.Cerberus.infrastructure.command.Command;
import com.hexi.Cerberus.infrastructure.command.CommandId;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Builder
public class UpdateFactorySiteDetailsCmd implements Command {
    CommandId id;
    FactorySiteID factorySiteId;
    String name;
    @Override
    public CommandId getId() {
        return id;
    }

    @Override
    public ValidationResult validate() {
        List<String> problems = new ArrayList<>();
        if(id == null) problems.add("Command id is null");
        if(factorySiteId == null) problems.add("FactorySite id is null");
        if(name == null) problems.add("Name is null");

        return new ValidationResult(problems);
    }

}
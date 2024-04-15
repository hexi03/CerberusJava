package com.hexi.Cerberus.domain.item.command;

import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.infrastructure.ValidationResult;
import com.hexi.Cerberus.infrastructure.command.Command;
import com.hexi.Cerberus.infrastructure.command.CommandId;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class UpdateItemCmd implements Command {
    CommandId id;
    ItemID itemId;
    String name;
    String units;

    @Override
    public CommandId getId() {
        return id;
    }

    @Override
    public ValidationResult validate() {
        List<String> problems = new ArrayList<>();
        if (id == null) problems.add("Command id is null");
        if (itemId == null) problems.add("Item id is null");
        if (name == null) problems.add("Name is null");
        if (units == null) problems.add("Units is null");

        return new ValidationResult(problems);
    }
}
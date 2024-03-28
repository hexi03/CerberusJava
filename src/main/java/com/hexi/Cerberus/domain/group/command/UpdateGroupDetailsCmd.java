package com.hexi.Cerberus.domain.group.command;

import com.hexi.Cerberus.domain.group.GroupID;
import com.hexi.Cerberus.infrastructure.ValidationResult;
import com.hexi.Cerberus.infrastructure.command.Command;
import com.hexi.Cerberus.infrastructure.command.CommandId;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class UpdateGroupDetailsCmd implements Command {
    CommandId id;
    GroupID groupId;
    String name;
    @Override
    public CommandId getId() {
        return id;
    }

    @Override
    public ValidationResult validate() {
        List<String> problems = new ArrayList<>();
        if(id == null) problems.add("Command id is null");
        if(groupId == null) problems.add("Group id is null");
        if(name == null) problems.add("Name is null");

        return new ValidationResult(problems);
    }
}
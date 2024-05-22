package com.hexi.Cerberus.domain.user.command;

import com.hexi.Cerberus.infrastructure.ValidationResult;
import com.hexi.Cerberus.infrastructure.command.Command;
import com.hexi.Cerberus.infrastructure.command.CommandId;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class CreateUserCmd implements Command {
    CommandId id;
    String name;
    String email;
    String password;

    @Override
    public CommandId getId() {
        return id;
    }

    @Override
    public ValidationResult validate() {
        List<String> problems = new ArrayList<>();
        if (id == null) problems.add("Command id is null");
        if (name == null) problems.add("Name is null");
        if (email == null) problems.add("Email is null");
        if (password == null) problems.add("Password is null");

        return new ValidationResult(problems);
    }


}
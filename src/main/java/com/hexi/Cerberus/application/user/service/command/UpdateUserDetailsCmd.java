package com.hexi.Cerberus.application.user.service.command;

import com.hexi.Cerberus.domain.user.UserID;
import com.hexi.Cerberus.infrastructure.ValidationResult;
import com.hexi.Cerberus.infrastructure.command.Command;
import com.hexi.Cerberus.infrastructure.command.CommandId;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class UpdateUserDetailsCmd implements Command {
    CommandId id;
    UserID userId;
    String name;
    String password;
    String email;

    @Override
    public CommandId getId() {
        return id;
    }

    @Override
    public ValidationResult validate() {
        List<String> problems = new ArrayList<>();
        if (id == null) problems.add("Command id is null");
        if (userId == null) problems.add("User id is null");
        if (name == null) problems.add("Name is null");
        if (email == null) problems.add("Email is null");
        if (password == null) problems.add("Password is null");


        return new ValidationResult(problems);
    }
}
package com.hexi.Cerberus.application.group.service.command;

import com.hexi.Cerberus.domain.group.GroupID;
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
public class GroupIncludeUsersCmd implements Command {
    CommandId id;
    GroupID groupId;
    List<UserID> users;

    @Override
    public CommandId getId() {
        return id;
    }

    @Override
    public ValidationResult validate() {
        List<String> problems = new ArrayList<>();
        if (id == null) problems.add("Command id is null");
        if (groupId == null) problems.add("Group id is null");
        if (users != null || users.stream().filter(userID -> userID == null).count() != 0)
            problems.add("User id(s) is null");

        return new ValidationResult(problems);
    }
}
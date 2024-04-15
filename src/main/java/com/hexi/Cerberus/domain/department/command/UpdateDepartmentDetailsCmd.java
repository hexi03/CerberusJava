package com.hexi.Cerberus.domain.department.command;

import com.hexi.Cerberus.domain.department.DepartmentID;
import com.hexi.Cerberus.infrastructure.ValidationResult;
import com.hexi.Cerberus.infrastructure.command.Command;
import com.hexi.Cerberus.infrastructure.command.CommandId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Builder
public class UpdateDepartmentDetailsCmd implements Command {
    CommandId id;
    DepartmentID departmentId;
    String name;

    @Override
    public CommandId getId() {
        return id;
    }

    @Override
    public ValidationResult validate() {
        List<String> problems = new ArrayList<>();
        if (id == null) problems.add("Command id is null");
        if (departmentId == null) problems.add("Department id is null");
        if (name == null) problems.add("Name is null");

        return new ValidationResult(problems);
    }
}
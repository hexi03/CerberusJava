package com.hexi.Cerberus.domain.warehouse.command;

import com.hexi.Cerberus.domain.warehouse.WareHouseID;
import com.hexi.Cerberus.infrastructure.ValidationResult;
import com.hexi.Cerberus.infrastructure.command.Command;
import com.hexi.Cerberus.infrastructure.command.CommandId;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Builder
public class UpdateWareHouseDetailsCmd implements Command {
    CommandId id;
    WareHouseID wareHouseId;
    String name;

    @Override
    public CommandId getId() {
        return id;
    }

    @Override
    public ValidationResult validate() {
        List<String> problems = new ArrayList<>();
        if (id == null) problems.add("Command id is null");
        if (wareHouseId == null) problems.add("WareHouse id is null");
        if (name == null) problems.add("Name is null");

        return new ValidationResult(problems);
    }
}
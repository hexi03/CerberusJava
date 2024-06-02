package com.hexi.Cerberus.application.factorysite.service.command;

import com.hexi.Cerberus.domain.factorysite.FactorySiteID;
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
public class UpdateFactorySiteSupplyCmd implements Command {
    CommandId id;
    FactorySiteID factorySiteId;
    List<WareHouseID> suppliers;

    @Override
    public CommandId getId() {
        return id;
    }

    @Override
    public ValidationResult validate() {
        List<String> problems = new ArrayList<>();
        if (id == null) problems.add("Command id is null");
        if (factorySiteId == null) problems.add("FactorySite id is null");
        if (suppliers == null || suppliers.stream().filter(wareHouseID -> wareHouseID == null).count() != 0)
            problems.add("Supplier id(s) is null");

        return new ValidationResult(problems);
    }
}
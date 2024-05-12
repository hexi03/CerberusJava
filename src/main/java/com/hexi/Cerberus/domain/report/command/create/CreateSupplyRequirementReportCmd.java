package com.hexi.Cerberus.domain.report.command.create;

import com.hexi.Cerberus.domain.factorysite.FactorySiteID;
import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.warehouse.WareHouseID;
import com.hexi.Cerberus.infrastructure.ValidationResult;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@SuperBuilder
public class CreateSupplyRequirementReportCmd extends CreateReportCmd {
    FactorySiteID factorySiteID;
    List<WareHouseID> targetWareHouseIds;
    Map<ItemID, Integer> items;

    @Override
    public ValidationResult validate() {
        List<String> problems = new ArrayList<>();
        if(id == null) problems.add("Command id is null");
        if (targetWareHouseIds.isEmpty() || targetWareHouseIds.stream().anyMatch(wareHouseID -> wareHouseID == null)) problems.add("Target warehouses id is null or includes null");
        if (items == null || items.entrySet().stream().filter(userID -> userID == null).count() != 0) problems.add("Item id(s) is null");

        return new ValidationResult(problems);
    }

}

package com.hexi.Cerberus.application.report.service.command.update;

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
public class UpdateSupplyRequirementReportCmd extends UpdateReportCmd {
    FactorySiteID factorySiteId;
    List<WareHouseID> targetWareHouseIds;
    Map<ItemID, Integer> items;


    @Override
    public ValidationResult validate() {
        List<String> problems = new ArrayList<>();
        super.validate(problems);
        if(factorySiteId == null) problems.add("Factory site id is null");
        if(items != null && items.entrySet().stream().filter(entry -> entry.getKey() == null).count() != 0) problems.add("Requirement id(s) is null");
        if (targetWareHouseIds == null || targetWareHouseIds.isEmpty() || targetWareHouseIds.stream().anyMatch(wareHouseID -> wareHouseID == null)) problems.add("Target warehouses id is null or includes null");
        return new ValidationResult(problems);
    }

}

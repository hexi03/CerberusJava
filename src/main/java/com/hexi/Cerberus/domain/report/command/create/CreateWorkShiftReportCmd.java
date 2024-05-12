package com.hexi.Cerberus.domain.report.command.create;

import com.hexi.Cerberus.domain.factorysite.FactorySiteID;
import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.product.ProductID;
import com.hexi.Cerberus.domain.warehouse.WareHouseID;
import com.hexi.Cerberus.infrastructure.ValidationResult;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@SuperBuilder
public class CreateWorkShiftReportCmd extends CreateReportCmd {
    FactorySiteID factorySiteId;
    List<WareHouseID> targetWareHouseIds;
    Map<ProductID, Integer> produced;
    Map<ItemID, Integer> losses;
    Map<ItemID, Integer> remains;

    Map<ItemID, Integer> unclaimedRemains;


    @Override
    public ValidationResult validate() {
        List<String> problems = new ArrayList<>();
        if(id == null) problems.add("Command id is null");
        if(factorySiteId == null) problems.add("FactorySite id is null");
        if (targetWareHouseIds.isEmpty() || targetWareHouseIds.stream().anyMatch(wareHouseID -> wareHouseID == null)) problems.add("Target warehouses id is null or includes null");

        return new ValidationResult(problems);
    }

}

package com.hexi.Cerberus.domain.report.command.update;

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
public class UpdateWorkShiftReportCmd extends UpdateReportCmd {
    FactorySiteID factorySiteId;
    List<WareHouseID> targetWareHouseIds;
    Map<ProductID, Integer> produced; //используем именно ProductID, потому-что нам нужна информация о конкретном производственном процессе
    Map<ItemID, Integer> losses;
    Map<ItemID, Integer> remains;
    Map<ItemID, Integer> unclaimedRemains;

    @Override
    public ValidationResult validate() {
        List<String> problems = new ArrayList<>();

        super.validate(problems);
        if(factorySiteId == null) problems.add("FactorySite id is null");
        if(produced != null && produced.entrySet().stream().filter(entry -> entry.getKey() == null).count() != 0) problems.add("Produced item id(s) is null");
        if(losses != null && losses.entrySet().stream().filter(entry -> entry.getKey() == null).count() != 0) problems.add("Lossed item id(s) is null");
        if(remains != null && remains.entrySet().stream().filter(entry -> entry.getKey() == null).count() != 0) problems.add("Remains item id(s) is null");
        if(unclaimedRemains != null && unclaimedRemains != null && unclaimedRemains.entrySet().stream().filter(entry -> entry.getKey() == null).count() != 0) problems.add("Unclaimed remains item id(s) is null");
        if (targetWareHouseIds == null || targetWareHouseIds.isEmpty() || targetWareHouseIds.stream().anyMatch(wareHouseID -> wareHouseID == null)) problems.add("Target warehouses id is null or includes null");
        return new ValidationResult(problems);
    }

}

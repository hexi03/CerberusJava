package com.hexi.Cerberus.application.report.service.command.create;

import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.domain.warehouse.WareHouseID;
import com.hexi.Cerberus.infrastructure.ValidationResult;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@SuperBuilder
public class CreateWorkShiftReplenishmentReportCmd extends CreateReportCmd {
    WareHouseID wareHouseId;
    ReportID workShiftReportId;
    Map<ItemID, Integer> items;
    Map<ItemID, Integer> unclaimedRemains;

    @Override
    public ValidationResult validate() {
        List<String> problems = new ArrayList<>();
        super.validate(problems);
        if(wareHouseId == null) problems.add("Warehouse id is null");
        if(workShiftReportId == null) problems.add("Work shift report id is null");
        if(items != null && items.entrySet().stream().filter(entry -> entry.getKey() == null).count() != 0) problems.add("Item id(s) is null");
        if(unclaimedRemains != null && unclaimedRemains.entrySet().stream().filter(entry -> entry.getKey() == null).count() != 0) problems.add("Unclaimed remain id(s) is null");
        return new ValidationResult(problems);
    }


}

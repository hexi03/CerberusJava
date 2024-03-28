package com.hexi.Cerberus.domain.report.command.update;

import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.domain.warehouse.WareHouseID;
import com.hexi.Cerberus.infrastructure.ValidationResult;
import com.hexi.Cerberus.infrastructure.command.Command;
import com.hexi.Cerberus.infrastructure.command.CommandId;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Getter
@SuperBuilder
public class UpdateInventarisationReportCmd extends UpdateReportCmd{
    WareHouseID wareHouseId;
    Map<ItemID, Integer> items;

    @Override
    public ValidationResult validate() {
        List<String> problems = new ArrayList<>();
//        if(id == null) problems.add("Command id is null");
//        if(itemId == null) problems.add("Item id is null");
//        if(requirements != null || requirements.stream().filter(userID -> userID == null).count() != 0) problems.add("Requireement id(s) is null");

        return new ValidationResult(problems);
    }


}

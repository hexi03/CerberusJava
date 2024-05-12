package com.hexi.Cerberus.domain.service.problems;

import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.infrastructure.StateProblem;

import java.util.Map;

public class InventarisationReportProblem implements StateProblem {
    Map<ItemID, Integer> differrence;
    public InventarisationReportProblem(Map<ItemID, Integer> differrence) {
        this.differrence = differrence;
    }


    @Override
    public String getMessage() {
        return "InventarisationReportProblem: " + differrence.toString();
    }
}

package com.hexi.Cerberus.domain.service.problems;

import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.infrastructure.StateProblem;

import java.util.Map;

public class WorkShiftConsumablesTooMuchProblem implements StateProblem {
    Map<ItemID, Integer> lostedOnSiteConsumablesNeg;
    public WorkShiftConsumablesTooMuchProblem(Map<ItemID, Integer> lostedOnSiteConsumablesNeg) {
        this.lostedOnSiteConsumablesNeg = lostedOnSiteConsumablesNeg;
    }


    @Override
    public String getMessage() {
        return "WorkShiftConsumablesTooMuchProblem: " + lostedOnSiteConsumablesNeg.toString();
    }
}

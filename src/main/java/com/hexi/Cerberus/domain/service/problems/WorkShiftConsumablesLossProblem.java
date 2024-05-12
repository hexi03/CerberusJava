package com.hexi.Cerberus.domain.service.problems;

import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.infrastructure.StateProblem;

import java.util.Map;

public class WorkShiftConsumablesLossProblem implements StateProblem {
    Map<ItemID, Integer> lostedOnSiteConsumables;
    public WorkShiftConsumablesLossProblem(Map<ItemID, Integer> lostedOnSiteConsumables) {
        this.lostedOnSiteConsumables = lostedOnSiteConsumables;
    }

    @Override
    public String getMessage() {
        return "WorkShiftConsumablesLossProblem: " + lostedOnSiteConsumables.toString();
    }
}

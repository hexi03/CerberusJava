package com.hexi.Cerberus.domain.service.problems;

import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.infrastructure.StateProblem;
import lombok.Getter;

import java.util.Map;
@Getter
public class WorkShiftConsumablesLossProblem implements StateProblem {
    Map<ItemID, Integer> lostedOnSiteConsumables;


    public WorkShiftConsumablesLossProblem(Map<ItemID, Integer> lostedOnSiteConsumables) {
        this.lostedOnSiteConsumables = lostedOnSiteConsumables;

    }
}

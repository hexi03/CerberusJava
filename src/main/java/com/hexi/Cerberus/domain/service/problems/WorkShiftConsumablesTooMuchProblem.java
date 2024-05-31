package com.hexi.Cerberus.domain.service.problems;

import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.infrastructure.StateProblem;
import lombok.Getter;

import java.util.Map;
@Getter
public class WorkShiftConsumablesTooMuchProblem implements StateProblem {
    Map<ItemID, Integer> lostedOnSiteConsumablesNeg;


    public WorkShiftConsumablesTooMuchProblem(Map<ItemID, Integer> lostedOnSiteConsumablesNeg) {
        this.lostedOnSiteConsumablesNeg = lostedOnSiteConsumablesNeg;
    }
}

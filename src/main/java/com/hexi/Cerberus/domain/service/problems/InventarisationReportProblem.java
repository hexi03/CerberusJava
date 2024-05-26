package com.hexi.Cerberus.domain.service.problems;

import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.infrastructure.StateProblem;

import java.util.Map;

public class InventarisationReportProblem implements StateProblem {
    ReportID invReportId;
    Map<ItemID, Integer> differrence;
    public InventarisationReportProblem(ReportID id, Map<ItemID, Integer> differrence) {
        this.differrence = differrence;
        invReportId = id;
    }


    @Override
    public String getMessage() {
        return "InventarisationReportProblem("+ invReportId.getId().toString() +"): " + differrence.toString();
    }
}

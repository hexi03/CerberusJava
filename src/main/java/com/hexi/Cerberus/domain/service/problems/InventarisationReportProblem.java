package com.hexi.Cerberus.domain.service.problems;

import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.infrastructure.StateProblem;
import lombok.Getter;

import java.util.Map;
@Getter
public class InventarisationReportProblem implements StateProblem {
    ReportID rep;
    Map<ItemID, Integer> differrence;
    public InventarisationReportProblem(ReportID id, Map<ItemID, Integer> differrence) {
        this.differrence = differrence;
        rep = id;
    }


   }

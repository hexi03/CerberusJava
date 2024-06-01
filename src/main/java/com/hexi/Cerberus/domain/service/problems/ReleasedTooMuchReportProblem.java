package com.hexi.Cerberus.domain.service.problems;

import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.infrastructure.StateProblem;
import lombok.Getter;

import java.util.List;
import java.util.Map;
@Getter
public class ReleasedTooMuchReportProblem implements StateProblem {
    ReportID rep;
    Map<ItemID, Integer> differrence;
    List<ReportID> affectedReportIds;

    public ReleasedTooMuchReportProblem(ReportID rep, Map<ItemID, Integer> differrence, List<ReportID> affectedReportIds) {
        this.rep = rep;
        this.differrence = differrence;
        this.affectedReportIds = affectedReportIds;
    }
}

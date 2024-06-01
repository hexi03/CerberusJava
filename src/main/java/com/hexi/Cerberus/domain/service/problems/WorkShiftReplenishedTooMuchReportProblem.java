package com.hexi.Cerberus.domain.service.problems;

import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.infrastructure.StateProblem;
import lombok.Getter;

import java.util.List;
import java.util.Map;
@Getter
public class WorkShiftReplenishedTooMuchReportProblem implements StateProblem {


    public enum By{
        Produced,
        UnclaimedRemains
    }
    ReportID rep; By by;
    Map<ItemID, Integer> differrence;

    List<ReportID> affectedReportIds;

    public WorkShiftReplenishedTooMuchReportProblem(ReportID rep, By by, Map<ItemID, Integer> differrence, List<ReportID> affectedReportIds) {
        this.rep = rep;
        this.by = by;
        this.affectedReportIds = affectedReportIds;
        this.differrence = differrence;
    }
}

package com.hexi.Cerberus.domain.service.problems;

import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.infrastructure.StateProblem;
import lombok.Getter;

import java.util.Map;
@Getter
public class WorkShiftReplenishedTooMuchReportProblem implements StateProblem {


    public enum By{
        Produced,
        UnclaimedRemains
    }
    ReportID rep; By by;
    Map<ItemID, Integer> differrence;

    public WorkShiftReplenishedTooMuchReportProblem(ReportID rep, By by, Map<ItemID, Integer> differrence) {
        this.rep = rep;
        this.by = by;
        this.differrence = differrence;
    }
}

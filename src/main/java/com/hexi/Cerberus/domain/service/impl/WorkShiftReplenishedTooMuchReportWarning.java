package com.hexi.Cerberus.domain.service.impl;

import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.domain.service.warnings.UnsatisfiedWorkShiftReportWarning;
import com.hexi.Cerberus.infrastructure.StateProblem;

public class WorkShiftReplenishedTooMuchReportWarning implements StateProblem {


    public enum By{
        Produced,
        UnclaimedRemains
    }
    ReportID rep; By by;
    public WorkShiftReplenishedTooMuchReportWarning(ReportID rep, By by) {
        this.rep = rep;
        this.by = by;
    }

    @Override
    public String getMessage() {
        return "WorkShiftReplenishedTooMuchReportWarning: " + by + " " + rep;
    }

}

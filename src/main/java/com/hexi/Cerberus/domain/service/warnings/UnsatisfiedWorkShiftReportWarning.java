package com.hexi.Cerberus.domain.service.warnings;

import com.hexi.Cerberus.domain.factorysite.FactorySite;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.infrastructure.StateWarning;

public class UnsatisfiedWorkShiftReportWarning implements StateWarning {
    public enum By{
        Produced,
        UnclaimedRemains
    }
    ReportID rep;
    By by;
    public UnsatisfiedWorkShiftReportWarning(ReportID rep, By by) {
        this.rep = rep;
        this.by = by;
    }

    @Override
    public String getMessage() {
        return "UnsatisfiedWorkShiftReportWarning: by" + by.toString() + " " + rep;
    }
}

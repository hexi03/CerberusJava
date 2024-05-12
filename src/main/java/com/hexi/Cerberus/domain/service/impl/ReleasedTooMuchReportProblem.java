package com.hexi.Cerberus.domain.service.impl;

import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.infrastructure.StateProblem;

public class ReleasedTooMuchReportProblem implements StateProblem {
    ReportID rep;
    public ReleasedTooMuchReportProblem(ReportID rep) {
        this.rep = rep;
    }

    @Override
    public String getMessage() {
        return "ReleasedTooMuchReportProblem: " + rep;
    }
}

package com.hexi.Cerberus.domain.service.warnings;

import com.hexi.Cerberus.domain.factorysite.FactorySite;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.infrastructure.StateWarning;

public class UnsatisfiedSupplyRequirementReportWarning implements StateWarning {
    FactorySite factorySite;
    ReportID rep;
    public UnsatisfiedSupplyRequirementReportWarning(ReportID rep) {
        this.factorySite = factorySite;
        this.rep = rep;
    }

    @Override
    public String getMessage() {
        return "UnsatisfiedSupplyRequirementReportWarning: " + rep;
    }
}

package com.hexi.Cerberus.domain.report.factorysite;

import com.hexi.Cerberus.domain.factorysite.FactorySite;
import com.hexi.Cerberus.domain.factorysite.FactorySiteID;
import com.hexi.Cerberus.domain.report.Report;

public interface FactorySiteReport extends Report {
    FactorySite getFactorySite();
}

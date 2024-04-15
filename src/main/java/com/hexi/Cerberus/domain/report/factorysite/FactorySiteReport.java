package com.hexi.Cerberus.domain.report.factorysite;

import com.hexi.Cerberus.domain.factorysite.FactorySiteID;
import com.hexi.Cerberus.domain.report.Report;

public interface FactorySiteReport extends Report {
    FactorySiteID getFactorySiteId();
}

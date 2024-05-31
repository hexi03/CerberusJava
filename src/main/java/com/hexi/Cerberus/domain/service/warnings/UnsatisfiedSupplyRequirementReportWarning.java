package com.hexi.Cerberus.domain.service.warnings;

import com.hexi.Cerberus.domain.factorysite.FactorySite;
import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.infrastructure.StateWarning;
import lombok.Getter;

import java.util.Map;
@Getter
public class UnsatisfiedSupplyRequirementReportWarning implements StateWarning {
    ReportID rep;
    Map<ItemID, Integer> items;

    public UnsatisfiedSupplyRequirementReportWarning(ReportID rep, Map<ItemID, Integer> items) {
        this.rep = rep;
        this.items = items;
    }
}

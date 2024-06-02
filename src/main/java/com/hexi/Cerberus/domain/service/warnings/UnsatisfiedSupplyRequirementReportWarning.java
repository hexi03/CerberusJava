package com.hexi.Cerberus.domain.service.warnings;

import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.infrastructure.StateWarning;
import lombok.Getter;

import java.util.List;
import java.util.Map;
@Getter
public class UnsatisfiedSupplyRequirementReportWarning implements StateWarning {
    ReportID rep;
    Map<ItemID, Integer> items;
    List<ReportID> affectedReportIds;

    public UnsatisfiedSupplyRequirementReportWarning(ReportID rep, Map<ItemID, Integer> items, List<ReportID> affectedReportIds) {
        this.rep = rep;
        this.items = items;
        this.affectedReportIds = affectedReportIds;
    }
}

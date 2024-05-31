package com.hexi.Cerberus.domain.service.warnings;

import com.hexi.Cerberus.domain.factorysite.FactorySite;
import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.domain.report.factorysite.WorkShiftReport;
import com.hexi.Cerberus.infrastructure.StateWarning;
import lombok.Getter;

import java.util.Map;
@Getter
public class UnsatisfiedWorkShiftReportWarning implements StateWarning {
    public enum By{
        Produced,
        UnclaimedRemains
    }
    ReportID rep;
    By by;
    Map<ItemID, Integer> items;

    public UnsatisfiedWorkShiftReportWarning(ReportID rep, By by, Map<ItemID, Integer> items) {
        this.rep = rep;
        this.by = by;
        this.items = items;
    }
}

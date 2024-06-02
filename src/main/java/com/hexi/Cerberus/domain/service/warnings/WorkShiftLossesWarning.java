package com.hexi.Cerberus.domain.service.warnings;

import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.infrastructure.StateWarning;
import lombok.Getter;

import java.util.Map;
@Getter
public class WorkShiftLossesWarning implements StateWarning {
    ReportID rep;
    Map<ItemID, Integer> losses;
    public WorkShiftLossesWarning(ReportID rep, Map<ItemID, Integer> losses) {
        this.losses = losses;
        this.rep = rep;
    }

}

package com.hexi.Cerberus.domain.service.warnings;

import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.infrastructure.StateWarning;
import lombok.Getter;

import java.util.Map;
@Getter
public class WorkShiftRemainsWarning implements StateWarning {
    ReportID rep;
    Map<ItemID, Integer> remains;
    public WorkShiftRemainsWarning(ReportID rep, Map<ItemID, Integer> remains) {
        this.remains = remains;
        this.rep = rep;
    }

}

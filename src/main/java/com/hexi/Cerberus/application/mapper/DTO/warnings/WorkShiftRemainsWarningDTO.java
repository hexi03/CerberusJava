package com.hexi.Cerberus.application.mapper.DTO.warnings;

import com.hexi.Cerberus.application.mapper.DTO.StateWarningDTO;
import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.report.ReportID;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
@Getter
@Setter
public class WorkShiftRemainsWarningDTO extends StateWarningDTO {
    private static final String WorkShiftRemainsWarning = "WorkShiftRemainsWarning";
    ReportID rep;
    Map<ItemID, Integer> remains;
    public WorkShiftRemainsWarningDTO(ReportID rep, Map<ItemID, Integer> remains) {
        super(WorkShiftRemainsWarning);
        this.remains = remains;
        this.rep = rep;
    }

    public WorkShiftRemainsWarningDTO() {
        super(WorkShiftRemainsWarning);
    }

}

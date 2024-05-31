package com.hexi.Cerberus.application.mapper.DTO.warnings;

import com.hexi.Cerberus.application.mapper.DTO.StateWarningDTO;
import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.report.ReportID;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
@Getter
@Setter
public class WorkShiftLossesWarningDTO extends StateWarningDTO {
    private static final String WorkShiftLossesWarning = "WorkShiftLossesWarning";
    ReportID rep;
    Map<ItemID, Integer> losses;
    public WorkShiftLossesWarningDTO(ReportID rep, Map<ItemID, Integer> losses) {
        super(WorkShiftLossesWarning);
        this.losses = losses;
        this.rep = rep;
    }

    public WorkShiftLossesWarningDTO() {
        super(WorkShiftLossesWarning);
    }

}

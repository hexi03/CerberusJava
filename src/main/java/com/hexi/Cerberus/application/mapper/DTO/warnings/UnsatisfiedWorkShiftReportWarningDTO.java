package com.hexi.Cerberus.application.mapper.DTO.warnings;

import com.hexi.Cerberus.application.mapper.DTO.StateWarningDTO;
import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.report.ReportID;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
@Getter
@Setter
public class UnsatisfiedWorkShiftReportWarningDTO extends StateWarningDTO {
    private static final String UnsatisfiedWorkShiftReportWarning = "UnsatisfiedWorkShiftReportWarning";

    public enum By{
        Produced,
        UnclaimedRemains
    }
    ReportID rep;
    By by;
    Map<ItemID, Integer> items;

    public UnsatisfiedWorkShiftReportWarningDTO(ReportID rep, By by, Map<ItemID, Integer> items) {
        super(UnsatisfiedWorkShiftReportWarning);
        this.rep = rep;
        this.by = by;
        this.items = items;
    }

    public UnsatisfiedWorkShiftReportWarningDTO() {
        super(UnsatisfiedWorkShiftReportWarning);
    }
}

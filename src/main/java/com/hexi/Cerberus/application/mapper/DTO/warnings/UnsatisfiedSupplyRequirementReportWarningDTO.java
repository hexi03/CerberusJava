package com.hexi.Cerberus.application.mapper.DTO.warnings;

import com.hexi.Cerberus.application.mapper.DTO.StateWarningDTO;
import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.report.ReportID;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
@Getter
@Setter
public class UnsatisfiedSupplyRequirementReportWarningDTO extends StateWarningDTO {
    private static final String UnsatisfiedSupplyRequirementReportWarning = "UnsatisfiedSupplyRequirementReportWarning";
    ReportID rep;
    Map<ItemID, Integer> items;

    public UnsatisfiedSupplyRequirementReportWarningDTO(ReportID rep, Map<ItemID, Integer> items) {
        super(UnsatisfiedSupplyRequirementReportWarning);
        this.rep = rep;
        this.items = items;
    }

    public UnsatisfiedSupplyRequirementReportWarningDTO() {
        super(UnsatisfiedSupplyRequirementReportWarning);
    }

}

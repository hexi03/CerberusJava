package com.hexi.Cerberus.domain.report.query.filter;

import com.hexi.Cerberus.domain.warehouse.WareHouseID;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class SupplyRequirementReportFilterCriteria extends FactorySiteReportFilterCriteria {
    WareHouseID targetWareHouseId;
    Boolean isUnsatisfied;

}

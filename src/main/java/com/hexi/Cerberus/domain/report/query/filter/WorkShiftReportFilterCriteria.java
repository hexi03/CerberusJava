package com.hexi.Cerberus.domain.report.query.filter;

import com.hexi.Cerberus.domain.warehouse.WareHouseID;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Optional;

@Getter
@SuperBuilder
public class WorkShiftReportFilterCriteria extends FactorySiteReportFilterCriteria {
    Boolean hasLosses;
    Boolean hasRemains;

    WareHouseID targetWareHouseId;
}

package com.hexi.Cerberus.adapter.web.rest.Report.DTO.create;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.hexi.Cerberus.adapter.web.rest.Consts;
import com.hexi.Cerberus.domain.factorysite.FactorySiteID;
import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.domain.warehouse.WareHouseID;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
@JsonTypeName(Consts.REPORT_WH_WORKSHIFT_REPLENISHMENT)
public class CreateWorkShiftReplenishmentReportDTO extends CreateReportDTO {
    WareHouseID wareHouseId;
    ReportID workShiftReportId;
    Map<ItemID, Integer> items;
    Map<ItemID, Integer> unclaimedRemains;
}

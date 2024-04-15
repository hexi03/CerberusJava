package com.hexi.Cerberus.adapter.web.rest.Report.DTO.details;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.hexi.Cerberus.adapter.web.rest.Consts;
import com.hexi.Cerberus.domain.factorysite.FactorySiteID;
import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.domain.warehouse.WareHouseID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@SuperBuilder
@JsonTypeName(Consts.REPORT_WH_WORKSHIFT_REPLENISHMENT)
public class DetailsWorkShiftReplenishmentReportDTO extends ReportDetailsDTO {
    WareHouseID wareHouseId;
    ReportID workShiftReportId;
    Map<ItemID, Integer> items;
    Map<ItemID, Integer> unclaimedRemains;
}

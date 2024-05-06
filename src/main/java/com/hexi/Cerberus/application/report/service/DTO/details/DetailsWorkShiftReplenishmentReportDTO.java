package com.hexi.Cerberus.application.report.service.DTO.details;

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
@Data
@SuperBuilder
@JsonTypeName(Consts.REPORT_WH_WORKSHIFT_REPLENISHMENT)
public class DetailsWorkShiftReplenishmentReportDTO extends ReportDetailsDTO {
    public final String type = Consts.REPORT_WH_WORKSHIFT_REPLENISHMENT;
    WareHouseID wareHouseId;
    ReportID workShiftReportId;
    Map<ItemID, Integer> items;
    Map<ItemID, Integer> unclaimedRemains;

}

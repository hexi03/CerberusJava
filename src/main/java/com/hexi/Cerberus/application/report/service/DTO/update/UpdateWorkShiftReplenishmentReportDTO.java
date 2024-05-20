package com.hexi.Cerberus.application.report.service.DTO.update;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.hexi.Cerberus.adapter.web.rest.Consts;
import com.hexi.Cerberus.domain.factorysite.FactorySiteID;
import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.domain.user.UserID;
import com.hexi.Cerberus.domain.warehouse.WareHouseID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeName(Consts.REPORT_WH_WORKSHIFT_REPLENISHMENT)
public class UpdateWorkShiftReplenishmentReportDTO extends UpdateReportDTO {
    WareHouseID wareHouseId;
    UserID creatorId;
    ReportID workShiftReportId;
    Map<ItemID, Integer> items;
    Map<ItemID, Integer> unclaimedRemains;
}

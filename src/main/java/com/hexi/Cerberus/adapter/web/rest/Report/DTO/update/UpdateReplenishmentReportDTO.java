package com.hexi.Cerberus.adapter.web.rest.Report.DTO.update;


import com.fasterxml.jackson.annotation.JsonTypeName;
import com.hexi.Cerberus.adapter.web.rest.Consts;
import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.warehouse.WareHouseID;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
@JsonTypeName(Consts.REPORT_WH_REPLENISHMENT)
public class UpdateReplenishmentReportDTO extends UpdateReportDTO {
    WareHouseID wareHouseId;
    Map<ItemID, Integer> items;
}

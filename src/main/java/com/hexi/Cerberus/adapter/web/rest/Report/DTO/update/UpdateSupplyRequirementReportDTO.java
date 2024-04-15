package com.hexi.Cerberus.adapter.web.rest.Report.DTO.update;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.hexi.Cerberus.adapter.web.rest.Consts;
import com.hexi.Cerberus.domain.factorysite.FactorySiteID;
import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.warehouse.WareHouseID;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
@JsonTypeName(Consts.REPORT_FS_SUPPLY_REQUIREMENT)
public class UpdateSupplyRequirementReportDTO extends UpdateReportDTO {
    FactorySiteID factorySiteId;
    WareHouseID targetWareHouseId;
    Map<ItemID, Integer> items;
}
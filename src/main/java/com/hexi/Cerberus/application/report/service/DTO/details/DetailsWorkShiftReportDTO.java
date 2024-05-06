package com.hexi.Cerberus.application.report.service.DTO.details;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.hexi.Cerberus.adapter.web.rest.Consts;
import com.hexi.Cerberus.domain.factorysite.FactorySiteID;
import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.product.ProductID;
import com.hexi.Cerberus.domain.warehouse.WareHouseID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Data
@SuperBuilder
@JsonTypeName(Consts.REPORT_FS_WORKSHIFT)
public class DetailsWorkShiftReportDTO extends ReportDetailsDTO {
    public final String type = Consts.REPORT_FS_WORKSHIFT;
    FactorySiteID factorySiteId;
    WareHouseID targetWareHouseId;
    Map<ProductID, Integer> produced;
    Map<ItemID, Integer> losses;
    Map<ItemID, Integer> remains;


}

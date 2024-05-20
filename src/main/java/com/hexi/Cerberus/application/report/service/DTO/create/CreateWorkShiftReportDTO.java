package com.hexi.Cerberus.application.report.service.DTO.create;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.hexi.Cerberus.adapter.web.rest.Consts;
import com.hexi.Cerberus.domain.factorysite.FactorySiteID;
import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.product.ProductID;
import com.hexi.Cerberus.domain.user.UserID;
import com.hexi.Cerberus.domain.warehouse.WareHouseID;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@JsonTypeName(Consts.REPORT_FS_WORKSHIFT)
public class CreateWorkShiftReportDTO extends CreateReportDTO {
    FactorySiteID factorySiteId;
    List<WareHouseID> targetWareHouseIds;
    Map<ProductID, Integer> produced;
    Map<ItemID, Integer> losses;
    Map<ItemID, Integer> remains;
    Map<ItemID, Integer> unclaimedRemains;


}

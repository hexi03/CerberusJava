package com.hexi.Cerberus.application.report.service.DTO.create;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.hexi.Cerberus.adapter.web.rest.Consts;
import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.user.UserID;
import com.hexi.Cerberus.domain.warehouse.WareHouseID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeName(Consts.REPORT_WH_SHIPMENT)
public class CreateShipmentReportDTO extends CreateReportDTO {
    WareHouseID wareHouseId;
    Map<ItemID, Integer> items;

}

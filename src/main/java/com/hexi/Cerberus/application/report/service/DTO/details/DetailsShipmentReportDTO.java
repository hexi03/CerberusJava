package com.hexi.Cerberus.application.report.service.DTO.details;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.hexi.Cerberus.adapter.web.rest.Consts;
import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.warehouse.WareHouseID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Data
@SuperBuilder
@JsonTypeName(Consts.REPORT_WH_SHIPMENT)
public class DetailsShipmentReportDTO extends ReportDetailsDTO {
    public final String type = Consts.REPORT_WH_SHIPMENT;
    WareHouseID wareHouseId;
    Map<ItemID, Integer> items;

}

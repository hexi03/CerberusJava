package com.hexi.Cerberus.adapter.web.rest.Report.DTO.details;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.hexi.Cerberus.adapter.web.rest.Consts;
import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.warehouse.WareHouseID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Data
@SuperBuilder
@JsonTypeName(Consts.REPORT_WH_INVENTARISATION)
public class DetailsInventarisationReportDTO extends ReportDetailsDTO {
    WareHouseID wareHouseId;
    Map<ItemID, Integer> items;

}

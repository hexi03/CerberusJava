package com.hexi.Cerberus.application.report.service.DTO.create;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.hexi.Cerberus.adapter.web.rest.Consts;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = CreateReportDTO.DISCRIMINATOR)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CreateInventarisationReportDTO.class, name = Consts.REPORT_WH_INVENTARISATION),
        @JsonSubTypes.Type(value = CreateWorkShiftReportDTO.class, name = Consts.REPORT_FS_WORKSHIFT),
        @JsonSubTypes.Type(value = CreateSupplyRequirementReportDTO.class, name = Consts.REPORT_FS_SUPPLY_REQUIREMENT),
        @JsonSubTypes.Type(value = CreateShipmentReportDTO.class, name = Consts.REPORT_WH_SHIPMENT),
        @JsonSubTypes.Type(value = CreateReplenishmentReportDTO.class, name = Consts.REPORT_WH_REPLENISHMENT),
        @JsonSubTypes.Type(value = CreateWorkShiftReplenishmentReportDTO.class, name = Consts.REPORT_WH_WORKSHIFT_REPLENISHMENT),
        @JsonSubTypes.Type(value = CreateReleaseReportDTO.class, name = Consts.REPORT_WH_RELEASE)

})
public abstract class CreateReportDTO {
    public static final String DISCRIMINATOR = "type";


}

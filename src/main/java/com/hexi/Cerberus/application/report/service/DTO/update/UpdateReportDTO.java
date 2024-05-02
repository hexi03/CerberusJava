package com.hexi.Cerberus.application.report.service.DTO.update;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.hexi.Cerberus.adapter.web.rest.Consts;
import com.hexi.Cerberus.domain.report.ReportID;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = UpdateInventarisationReportDTO.class, name = Consts.REPORT_WH_INVENTARISATION),
        @JsonSubTypes.Type(value = UpdateWorkShiftReportDTO.class, name = Consts.REPORT_FS_WORKSHIFT),
        @JsonSubTypes.Type(value = UpdateSupplyRequirementReportDTO.class, name = Consts.REPORT_FS_SUPPLY_REQUIREMENT),
        @JsonSubTypes.Type(value = UpdateShipmentReportDTO.class, name = Consts.REPORT_WH_SHIPMENT),
        @JsonSubTypes.Type(value = UpdateReplenishmentReportDTO.class, name = Consts.REPORT_WH_REPLENISHMENT),
        @JsonSubTypes.Type(value = UpdateWorkShiftReplenishmentReportDTO.class, name = Consts.REPORT_WH_WORKSHIFT_REPLENISHMENT),
        @JsonSubTypes.Type(value = UpdateReleaseReportDTO.class, name = Consts.REPORT_WH_RELEASE)

})
@NoArgsConstructor
@Data
public abstract class UpdateReportDTO {
    ReportID reportId;
}

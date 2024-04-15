package com.hexi.Cerberus.adapter.web.rest.Report;

import com.hexi.Cerberus.adapter.web.rest.Consts;
import com.hexi.Cerberus.adapter.web.rest.Report.DTO.create.*;
import com.hexi.Cerberus.adapter.web.rest.Report.DTO.details.ReportDetails;
import com.hexi.Cerberus.adapter.web.rest.Report.DTO.update.*;
import com.hexi.Cerberus.application.report.service.ReportManagementService;
import com.hexi.Cerberus.domain.report.Report;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.domain.report.command.create.*;
import com.hexi.Cerberus.domain.report.command.update.*;
import com.hexi.Cerberus.domain.report.query.filter.*;
import com.hexi.Cerberus.domain.report.query.sort.*;
import com.hexi.Cerberus.infrastructure.adapter.DrivingAdapter;
import com.hexi.Cerberus.infrastructure.command.CommandId;
import com.hexi.Cerberus.infrastructure.query.PagingCriteria;
import com.hexi.Cerberus.infrastructure.query.Query;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@DrivingAdapter
@RequestMapping("/api/report")
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@AllArgsConstructor
@Slf4j
public class ReportController {
    public final ReportManagementService reportManagementService;
    public final DomainToDTOMapper domainToDTOMapper;

    static Query toQuery(
            @PathVariable(required = false) ReportID id,
            @PathVariable(required = false) Integer shift,
            @PathVariable(required = false) Integer count,
            @PathVariable(required = false) Boolean descending,
            @PathVariable(required = false) String sortBy,
            @PathVariable(required = false) String typeCriteria
    ) {
        // Определение PagingCriteria
        PagingCriteria pagingCriteria = new PagingCriteria(
                shift, count
        );

        // Определение SortCriteria
        ReportSortCriteria sortCriteria = null;
        if (typeCriteria.equals(Consts.REPORT_WH_INVENTARISATION)) {
            sortCriteria = InventarisationReportSortCriteria
                    .builder()
                    .sortBy(InventarisationReportSortCriteria.SortBy.valueOf(sortBy))
                    .descending(descending)
                    .build();
        } else if (typeCriteria.equals(Consts.REPORT_WH_RELEASE)) {
            sortCriteria = ReleaseReportSortCriteria
                    .builder()
                    .sortBy(ReleaseReportSortCriteria.SortBy.valueOf(sortBy))
                    .descending(descending)
                    .build();
        } else if (typeCriteria.equals(Consts.REPORT_WH_REPLENISHMENT)) {
            sortCriteria = ReplenishmentReportSortCriteria
                    .builder()
                    .sortBy(ReplenishmentReportSortCriteria.SortBy.valueOf(sortBy))
                    .descending(descending)
                    .build();
        } else if (typeCriteria.equals(Consts.REPORT_WH_SHIPMENT)) {
            sortCriteria = ShipmentReportSortCriteria
                    .builder()
                    .sortBy(ShipmentReportSortCriteria.SortBy.valueOf(sortBy))
                    .descending(descending)
                    .build();
        } else if (typeCriteria.equals(Consts.REPORT_WH_WORKSHIFT_REPLENISHMENT)) {
            sortCriteria = WorkShiftReplenishmentReportSortCriteria
                    .builder()
                    .sortBy(WorkShiftReplenishmentReportSortCriteria.SortBy.valueOf(sortBy))
                    .descending(descending)
                    .build();
        } else if (typeCriteria.equals(Consts.REPORT_FS_SUPPLY_REQUIREMENT)) {
            sortCriteria = SupplyRequirementReportSortCriteria
                    .builder()
                    .sortBy(SupplyRequirementReportSortCriteria.SortBy.valueOf(sortBy))
                    .descending(descending)
                    .build();
        } else if (typeCriteria.equals(Consts.REPORT_FS_WORKSHIFT)) {
            sortCriteria = WorkShiftReportSortCriteria
                    .builder()
                    .sortBy(WorkShiftReportSortCriteria.SortBy.valueOf(sortBy))
                    .descending(descending)
                    .build();
        } else {
            throw new IllegalArgumentException("Unknown report type: " + typeCriteria);
        }

        // Определение FilterCriteria
        ReportFilterCriteria filterCriteria = null;
        if (typeCriteria.equals(Consts.REPORT_WH_INVENTARISATION)) {
            filterCriteria = InventarisationReportFilterCriteria.builder().build();
        } else if (typeCriteria.equals(Consts.REPORT_WH_RELEASE)) {
            filterCriteria = ReleaseReportFilterCriteria.builder().build();
        } else if (typeCriteria.equals(Consts.REPORT_WH_REPLENISHMENT)) {
            filterCriteria = ReplenishmentReportFilterCriteria.builder().build();
        } else if (typeCriteria.equals(Consts.REPORT_WH_SHIPMENT)) {
            filterCriteria = ShipmentReportFilterCriteria.builder().build();
        } else if (typeCriteria.equals(Consts.REPORT_WH_WORKSHIFT_REPLENISHMENT)) {
            filterCriteria = WorkShiftReplenishmentReportFilterCriteria.builder().build();
        } else if (typeCriteria.equals(Consts.REPORT_FS_SUPPLY_REQUIREMENT)) {
            filterCriteria = SupplyRequirementReportFilterCriteria.builder().build();
        } else if (typeCriteria.equals(Consts.REPORT_FS_WORKSHIFT)) {
            filterCriteria = WorkShiftReportFilterCriteria.builder().build();
        } else {
            throw new IllegalArgumentException("Unknown report type: " + typeCriteria);
        }

        return new Query(filterCriteria, sortCriteria, pagingCriteria);
    }


    //FetchOne and FatchAll Copbined
    @GetMapping("/fetch")
    public ResponseEntity<List<ReportDetails>> fetch(
            @PathVariable(required = false) ReportID id,
            @PathVariable(required = false) Integer shift,
            @PathVariable(required = false) Integer count,
            @PathVariable(required = false) Boolean descending,
            @PathVariable(required = false) String sortBy,
            @PathVariable(required = false) String typeCriteria
    ) {
        log.debug(id.toString());

        List<ReportDetails> reportDetails;

        if (id == null) {
            // Fetch all reports with pagination and sorting
            Query query = toQuery(
                    id, shift, count, descending, sortBy, typeCriteria
            );

            List<Report> reports = reportManagementService.fetch(query);
            reportDetails = domainToDTOMapper.toReportDetails(reports);
        } else {
            // Fetch a single report by ID
            Report report = reportManagementService.fetchById(id).orElseThrow();
            if (report == null) {
                return ResponseEntity.notFound().build();  // Handle case when report not found
            }
            reportDetails = List.of(domainToDTOMapper.toReportDetails(report));
        }

        return ResponseEntity.ok(reportDetails);
    }


    @PostMapping("/append")
    public ResponseEntity<Void> appendReport(CreateReportDTO dto) {
        if (dto instanceof CreateSupplyRequirementReportDTO) {
            return append((CreateSupplyRequirementReportDTO) dto);
        } else if (dto instanceof CreateReleaseReportDTO) {
            return append((CreateReleaseReportDTO) dto);
        } else if (dto instanceof CreateInventarisationReportDTO) {
            return append((CreateInventarisationReportDTO) dto);
        } else if (dto instanceof CreateReplenishmentReportDTO) {
            return append((CreateReplenishmentReportDTO) dto);
        } else if (dto instanceof CreateShipmentReportDTO) {
            return append((CreateShipmentReportDTO) dto);
        } else if (dto instanceof CreateWorkShiftReplenishmentReportDTO) {
            return append((CreateWorkShiftReplenishmentReportDTO) dto);
        } else if (dto instanceof CreateWorkShiftReportDTO) {
            return append((CreateWorkShiftReportDTO) dto);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<Void> append() {
        return ResponseEntity.internalServerError().build();
    }

    public ResponseEntity<Void> append(CreateSupplyRequirementReportDTO dto) {
        reportManagementService.createReport(
                CreateSupplyRequirementReportCmd
                        .builder()
                        .id(CommandId.generate())
                        .factorySiteID(dto.getFactorySiteId())
                        .targetWareHouseId(dto.getTargetWareHouseId())
                        .items(dto.getItems()).build()
        );
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> append(CreateReleaseReportDTO dto) {
        reportManagementService.createReport(
                CreateReleaseReportCmd
                        .builder()
                        .id(CommandId.generate())
                        .supplyReqReportId(dto.getSupplyReqReportId())
                        .wareHouseId(dto.getWareHouseId())
                        .items(dto.getItems()).build()
        );
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> append(CreateInventarisationReportDTO dto) {
        reportManagementService.createReport(
                CreateInventarisationReportCmd
                        .builder()
                        .id(CommandId.generate())
                        .wareHouseId(dto.getWareHouseId())
                        .items(dto.getItems()).build()
        );
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> append(CreateReplenishmentReportDTO dto) {
        reportManagementService.createReport(
                CreateReplenishmentReportCmd
                        .builder()
                        .id(CommandId.generate())
                        .wareHouseId(dto.getWareHouseId())
                        .items(dto.getItems()).build()
        );
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> append(CreateShipmentReportDTO dto) {
        reportManagementService.createReport(
                CreateShipmentReportCmd
                        .builder()
                        .id(CommandId.generate())
                        .wareHouseId(dto.getWareHouseId())
                        .items(dto.getItems()).build()
        );
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> append(CreateWorkShiftReplenishmentReportDTO dto) {
        reportManagementService.createReport(
                CreateWorkShiftReplenishmentReportCmd
                        .builder()
                        .id(CommandId.generate())
                        .wareHouseId(dto.getWareHouseId())
                        .workShiftReportId(dto.getWorkShiftReportId())
                        .unclaimedRemains(dto.getUnclaimedRemains())
                        .items(dto.getItems()).build()
        );
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> append(CreateWorkShiftReportDTO dto) {
        reportManagementService.createReport(
                CreateWorkShiftReportCmd
                        .builder()
                        .id(CommandId.generate())
                        .factorySiteId(dto.getFactorySiteId())
                        .targetWareHouseId(dto.getTargetWareHouseId())
                        .remains(dto.getRemains())
                        .produced(dto.getProduced())
                        .losses(dto.getLosses())
                        .build()
        );
        return ResponseEntity.ok().build();
    }


    @PostMapping("/update")
    public ResponseEntity<Void> updateReport(UpdateReportDTO dto) {
        if (dto instanceof UpdateSupplyRequirementReportDTO) {
            return update((UpdateSupplyRequirementReportDTO) dto);
        } else if (dto instanceof UpdateReleaseReportDTO) {
            return update((UpdateReleaseReportDTO) dto);
        } else if (dto instanceof UpdateInventarisationReportDTO) {
            return update((UpdateInventarisationReportDTO) dto);
        } else if (dto instanceof UpdateReplenishmentReportDTO) {
            return update((UpdateReplenishmentReportDTO) dto);
        } else if (dto instanceof UpdateShipmentReportDTO) {
            return update((UpdateShipmentReportDTO) dto);
        } else if (dto instanceof UpdateWorkShiftReplenishmentReportDTO) {
            return update((UpdateWorkShiftReplenishmentReportDTO) dto);
        } else if (dto instanceof UpdateWorkShiftReportDTO) {
            return update((UpdateWorkShiftReportDTO) dto);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<Void> update() {
        return ResponseEntity.internalServerError().build();
    }

    public ResponseEntity<Void> update(UpdateSupplyRequirementReportDTO dto) {
        reportManagementService.updateReport(
                UpdateSupplyRequirementReportCmd
                        .builder()
                        .id(CommandId.generate())
                        .reportID(dto.getReportId())
                        .factorySiteID(dto.getFactorySiteId())
                        .targetWareHouseId(dto.getTargetWareHouseId())
                        .items(dto.getItems()).build()
        );
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> update(UpdateReleaseReportDTO dto) {
        reportManagementService.updateReport(
                UpdateReleaseReportCmd
                        .builder()
                        .id(CommandId.generate())
                        .reportID(dto.getReportId())
                        .supplyReqReportId(dto.getSupplyReqReportId())
                        .wareHouseId(dto.getWareHouseId())
                        .items(dto.getItems()).build()
        );
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> update(UpdateInventarisationReportDTO dto) {
        reportManagementService.updateReport(
                UpdateInventarisationReportCmd
                        .builder()
                        .id(CommandId.generate())
                        .reportID(dto.getReportId())
                        .wareHouseId(dto.getWareHouseId())
                        .items(dto.getItems()).build()
        );
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> update(UpdateReplenishmentReportDTO dto) {
        reportManagementService.updateReport(
                UpdateReplenishmentReportCmd
                        .builder()
                        .id(CommandId.generate())
                        .reportID(dto.getReportId())
                        .wareHouseId(dto.getWareHouseId())
                        .items(dto.getItems()).build()
        );
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> update(UpdateShipmentReportDTO dto) {
        reportManagementService.updateReport(
                UpdateShipmentReportCmd
                        .builder()
                        .reportID(dto.getReportId())
                        .id(CommandId.generate())
                        .wareHouseId(dto.getWareHouseId())
                        .items(dto.getItems()).build()
        );
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> update(UpdateWorkShiftReplenishmentReportDTO dto) {
        reportManagementService.updateReport(
                UpdateWorkShiftReplenishmentReportCmd
                        .builder()
                        .id(CommandId.generate())
                        .reportID(dto.getReportId())
                        .wareHouseId(dto.getWareHouseId())
                        .workShiftReportId(dto.getWorkShiftReportId())
                        .unclaimedRemains(dto.getUnclaimedRemains())
                        .items(dto.getItems()).build()
        );
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> update(UpdateWorkShiftReportDTO dto) {
        reportManagementService.updateReport(
                UpdateWorkShiftReportCmd
                        .builder()
                        .id(CommandId.generate())
                        .reportID(dto.getReportId())
                        .factorySiteId(dto.getFactorySiteId())
                        .targetWareHouseId(dto.getTargetWareHouseId())
                        .remains(dto.getRemains())
                        .produced(dto.getProduced())
                        .losses(dto.getLosses())
                        .build()
        );
        return ResponseEntity.ok().build();
    }

}



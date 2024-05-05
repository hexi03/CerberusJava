package com.hexi.Cerberus.adapter.web.rest.Report;

import com.hexi.Cerberus.application.report.service.DTO.create.*;
import com.hexi.Cerberus.application.report.service.DTO.details.*;
import com.hexi.Cerberus.application.report.service.DTO.update.*;
import com.hexi.Cerberus.application.report.service.ReportManagementService;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.domain.report.command.create.*;
import com.hexi.Cerberus.domain.report.command.update.*;
import com.hexi.Cerberus.infrastructure.adapter.DrivingAdapter;
import com.hexi.Cerberus.infrastructure.command.CommandId;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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



    //FetchOne and FetchAll Combined
    @GetMapping("/fetch")
    public ResponseEntity<List<ReportDetails>> fetch(
            @PathVariable(required = false) ReportID id,
            @PathVariable(required = false) ReportID key,
            @PathVariable(required = false) Integer count,
            @PathVariable(required = false) Boolean descending,
            @PathVariable(required = false) String sortBy,
            @PathVariable(required = false) String typeCriteria
    ) {


        List<ReportDetails> reportDetails;

        if (id == null) {
            log.debug(id.toString());
            // Fetch all reports with pagination and sorting


            List<ReportDetails> reports = reportManagementService.fetch(key, count, descending, sortBy, typeCriteria);
            reportDetails = reports;
        } else {
            // Fetch a single report by ID
            ReportDetails report = reportManagementService.fetchById(id).orElseThrow();
            if (report == null) {
                return ResponseEntity.notFound().build();  // Handle case when report not found
            }
            reportDetails = List.of(report);
        }

        return ResponseEntity.ok(reportDetails);
    }


    @PostMapping("/append")
    public ResponseEntity<ReportID> appendReport(@RequestBody CreateReportDTO dto) {
        log.info(dto.toString());
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


    public ResponseEntity<ReportID> append(CreateSupplyRequirementReportDTO dto) {
        ReportID id = reportManagementService.createReport(
                CreateSupplyRequirementReportCmd
                        .builder()
                        .id(CommandId.generate())
                        .factorySiteID(dto.getFactorySiteId())
                        .targetWareHouseId(dto.getTargetWareHouseId())
                        .items(dto.getItems()).build()
        ).getId();
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    public ResponseEntity<ReportID> append(CreateReleaseReportDTO dto) {
        ReportID id = reportManagementService.createReport(
                CreateReleaseReportCmd
                        .builder()
                        .id(CommandId.generate())
                        .supplyReqReportId(dto.getSupplyReqReportId())
                        .wareHouseId(dto.getWareHouseId())
                        .items(dto.getItems()).build()
        ).getId();
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    public ResponseEntity<ReportID> append(CreateInventarisationReportDTO dto) {
        ReportID id = reportManagementService.createReport(
                CreateInventarisationReportCmd
                        .builder()
                        .id(CommandId.generate())
                        .wareHouseId(dto.getWareHouseId())
                        .items(dto.getItems()).build()
        ).getId();
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    public ResponseEntity<ReportID> append(CreateReplenishmentReportDTO dto) {
        ReportID id = reportManagementService.createReport(
                CreateReplenishmentReportCmd
                        .builder()
                        .id(CommandId.generate())
                        .wareHouseId(dto.getWareHouseId())
                        .items(dto.getItems()).build()
        ).getId();
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    public ResponseEntity<ReportID> append(CreateShipmentReportDTO dto) {
        ReportID id = reportManagementService.createReport(
                CreateShipmentReportCmd
                        .builder()
                        .id(CommandId.generate())
                        .wareHouseId(dto.getWareHouseId())
                        .items(dto.getItems()).build()
        ).getId();
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    public ResponseEntity<ReportID> append(CreateWorkShiftReplenishmentReportDTO dto) {
        ReportID id = reportManagementService.createReport(
                CreateWorkShiftReplenishmentReportCmd
                        .builder()
                        .id(CommandId.generate())
                        .wareHouseId(dto.getWareHouseId())
                        .workShiftReportId(dto.getWorkShiftReportId())
                        .unclaimedRemains(dto.getUnclaimedRemains())
                        .items(dto.getItems()).build()
        ).getId();
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    public ResponseEntity<ReportID> append(CreateWorkShiftReportDTO dto) {
        ReportID id = reportManagementService.createReport(
                CreateWorkShiftReportCmd
                        .builder()
                        .id(CommandId.generate())
                        .factorySiteId(dto.getFactorySiteId())
                        .targetWareHouseId(dto.getTargetWareHouseId())
                        .remains(dto.getRemains())
                        .produced(dto.getProduced())
                        .losses(dto.getLosses())
                        .build()
        ).getId();
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }


    @PostMapping("/update")
    public ResponseEntity<Void> updateReport(@RequestBody UpdateReportDTO dto) {
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
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
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
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
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
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
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
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
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
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
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
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
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
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}



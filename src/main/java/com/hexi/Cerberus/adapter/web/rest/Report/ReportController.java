package com.hexi.Cerberus.adapter.web.rest.Report;

import com.hexi.Cerberus.application.report.service.DTO.create.*;
import com.hexi.Cerberus.application.report.service.DTO.details.ReportDetails;
import com.hexi.Cerberus.application.report.service.DTO.update.*;
import com.hexi.Cerberus.application.report.service.ReportManagementService;
import com.hexi.Cerberus.application.report.service.command.create.*;
import com.hexi.Cerberus.application.report.service.command.update.*;
import com.hexi.Cerberus.application.user.service.DTO.UserDetailsDTO;
import com.hexi.Cerberus.application.user.service.UserManagementService;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.domain.user.UserID;
import com.hexi.Cerberus.infrastructure.adapter.DrivingAdapter;
import com.hexi.Cerberus.infrastructure.command.CommandId;
import com.hexi.Cerberus.infrastructure.entity.UUIDBasedEntityID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@DrivingAdapter
@RequestMapping("/api/report")
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@AllArgsConstructor
@Slf4j
public class ReportController {
    public final ReportManagementService reportManagementService;
    public final UserManagementService userManagementService;



    //FetchOne and FetchAll Combined
    @GetMapping("/fetch")
    public ResponseEntity<List<ReportDetails>> fetch(
            @RequestParam(required = false) ReportID id,
            @RequestParam(required = false) UUIDBasedEntityID locationSpecificId,
            @RequestParam(required = false) ReportID key,
            @RequestParam(required = false) Integer count,
            @RequestParam(required = false) boolean descending,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String typeCriteria
    ) {


        List<ReportDetails> reportDetails;

        if (id == null) {
            // Fetch all reports with pagination and sorting


            List<ReportDetails> reports = reportManagementService.fetch(key, count, descending, sortBy, typeCriteria, locationSpecificId);
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

        if (dto.getCreatorId() == null){
            AbstractAuthenticationToken token = (AbstractAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            dto.setCreatorId(new UserID((UUIDBasedEntityID) token.getPrincipal()));
        }

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
                        .creatorId(dto.getCreatorId()).createdAt(dto.getCreatedAt())
                        .factorySiteId(dto.getFactorySiteId())
                        .targetWareHouseIds(dto.getTargetWareHouseIds())
                        .items(dto.getItems()).build()
        ).getId();
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    public ResponseEntity<ReportID> append(CreateReleaseReportDTO dto) {
        ReportID id = reportManagementService.createReport(
                CreateReleaseReportCmd
                        .builder()
                        .id(CommandId.generate())
                        .creatorId(dto.getCreatorId()).createdAt(dto.getCreatedAt())
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
                        .creatorId(dto.getCreatorId()).createdAt(dto.getCreatedAt())
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
                        .creatorId(dto.getCreatorId()).createdAt(dto.getCreatedAt())
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
                        .creatorId(dto.getCreatorId()).createdAt(dto.getCreatedAt())
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
                        .creatorId(dto.getCreatorId()).createdAt(dto.getCreatedAt())
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
                        .creatorId(dto.getCreatorId()).createdAt(dto.getCreatedAt())
                        .factorySiteId(dto.getFactorySiteId())
                        .targetWareHouseIds(dto.getTargetWareHouseIds())
                        .remains(dto.getRemains())
                        .unclaimedRemains(dto.getUnclaimedRemains())
                        .produced(dto.getProduced())
                        .losses(dto.getLosses())
                        .build()
        ).getId();
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }


    @PutMapping("/update")
    public ResponseEntity<Void> updateReport(@RequestBody UpdateReportDTO dto) {

        log.info(dto.toString());

        if (dto.getCreatorId() == null){
            AbstractAuthenticationToken token = (AbstractAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            dto.setCreatorId(new UserID((UUIDBasedEntityID) token.getPrincipal()));
        }

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
                        .creatorId(dto.getCreatorId()).createdAt(dto.getCreatedAt())
                        .reportId(dto.getId())
                        .factorySiteId(dto.getFactorySiteId())
                        .targetWareHouseIds(dto.getTargetWareHouseIds())
                        .items(dto.getItems()).build()
        );
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public ResponseEntity<Void> update(UpdateReleaseReportDTO dto) {
        reportManagementService.updateReport(
                UpdateReleaseReportCmd
                        .builder()
                        .id(CommandId.generate())
                        .creatorId(dto.getCreatorId()).createdAt(dto.getCreatedAt())
                        .reportId(dto.getId())
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
                        .creatorId(dto.getCreatorId()).createdAt(dto.getCreatedAt())
                        .reportId(dto.getId())
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
                        .creatorId(dto.getCreatorId()).createdAt(dto.getCreatedAt())
                        .reportId(dto.getId())
                        .wareHouseId(dto.getWareHouseId())
                        .items(dto.getItems()).build()
        );
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public ResponseEntity<Void> update(UpdateShipmentReportDTO dto) {
        reportManagementService.updateReport(
                UpdateShipmentReportCmd
                        .builder()
                        .reportId(dto.getId())
                        .id(CommandId.generate())
                        .creatorId(dto.getCreatorId()).createdAt(dto.getCreatedAt())
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
                        .creatorId(dto.getCreatorId()).createdAt(dto.getCreatedAt())
                        .reportId(dto.getId())
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
                        .creatorId(dto.getCreatorId()).createdAt(dto.getCreatedAt())
                        .reportId(dto.getId())
                        .factorySiteId(dto.getFactorySiteId())
                        .targetWareHouseIds(dto.getTargetWareHouseIds())
                        .remains(dto.getRemains())
                        .unclaimedRemains(dto.getUnclaimedRemains())
                        .produced(dto.getProduced())
                        .losses(dto.getLosses())
                        .build()
        );
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}



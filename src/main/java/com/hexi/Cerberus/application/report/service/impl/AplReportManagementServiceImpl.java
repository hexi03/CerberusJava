package com.hexi.Cerberus.application.report.service.impl;

import com.hexi.Cerberus.adapter.web.rest.Consts;
import com.hexi.Cerberus.application.report.service.DTO.details.ReportDetails;
import com.hexi.Cerberus.application.report.service.ReportDomainToDTOMapper;
import com.hexi.Cerberus.application.report.service.ReportManagementService;
import com.hexi.Cerberus.application.report.service.command.update.*;
import com.hexi.Cerberus.domain.department.DepartmentID;
import com.hexi.Cerberus.domain.factorysite.FactorySiteID;
import com.hexi.Cerberus.domain.report.Report;
import com.hexi.Cerberus.domain.report.ReportFactory;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.domain.report.ReportModifier;
import com.hexi.Cerberus.application.report.service.command.create.CreateReportCmd;
import com.hexi.Cerberus.domain.report.factorysite.FactorySiteReport;
import com.hexi.Cerberus.domain.report.factorysite.SupplyRequirementReport;
import com.hexi.Cerberus.domain.report.factorysite.WorkShiftReport;
import com.hexi.Cerberus.domain.report.query.filter.*;
import com.hexi.Cerberus.domain.report.query.sort.*;
import com.hexi.Cerberus.domain.report.repository.ReportRepository;
import com.hexi.Cerberus.domain.report.warehouse.*;
import com.hexi.Cerberus.domain.warehouse.WareHouseID;
import com.hexi.Cerberus.infrastructure.entity.EntityID;
import com.hexi.Cerberus.infrastructure.entity.UUIDBasedEntityID;
import com.hexi.Cerberus.infrastructure.messaging.MessagePublisher;
import com.hexi.Cerberus.infrastructure.query.PagingCriteria;
import com.hexi.Cerberus.infrastructure.query.Query;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class AplReportManagementServiceImpl implements ReportManagementService {
    public final ReportRepository reportRepository;
    public final MessagePublisher messagePublisher;
    public final MutableAclService aclService;
    public final ReportFactory reportFactory;
    public final ReportModifier reportModifier;
    public final ReportDomainToDTOMapper reportDomainToDTOMapper;

    static Query toQuery(
            EntityID key,
            Integer count,
            boolean descending,
            String sortBy,
            String typeCriteria,
            EntityID locationSpecificId

    ) {
        log.info(key != null ? key.toString() : null);log.info(count != null ?count.toString() : null);log.info(descending ? "descending": "ascending");log.info(sortBy);log.info(typeCriteria);
        // Определение PagingCriteria
        PagingCriteria pagingCriteria = new PagingCriteria(
                key, count
        );

        // Определение типа запроса
        ReportSortCriteria sortCriteria = null;
        ReportFilterCriteria filterCriteria = null;
        Class<? extends Report> entityType = null;
        if (typeCriteria != null)
        switch (typeCriteria) {
            case Consts.REPORT_WH_INVENTARISATION:
                if (sortBy != null)
                sortCriteria = InventarisationReportSortCriteria.builder()
                        .sortBy(List.of(InventarisationReportSortCriteria.SortBy.valueOf(sortBy)))
                        .sortType(List.of( descending ? ReportSortCriteria.SortType.DESCENDING : ReportSortCriteria.SortType.ASCENDING))
                        .build();

                filterCriteria = InventarisationReportFilterCriteria
                        .builder()
                        .warehouseId(locationSpecificId != null ? new WareHouseID((UUIDBasedEntityID) locationSpecificId) : null)
                        .build();
                entityType = InventarisationReport.class;
                break;
            case Consts.REPORT_WH_RELEASE:
                if (sortBy != null)
                sortCriteria = ReleaseReportSortCriteria.builder()
                        .sortBy(List.of(ReleaseReportSortCriteria.SortBy.valueOf(sortBy)))
                        .sortType(List.of( descending ? ReportSortCriteria.SortType.DESCENDING : ReportSortCriteria.SortType.ASCENDING))
                        .build();
                filterCriteria = ReleaseReportFilterCriteria
                        .builder()
                        .warehouseId(locationSpecificId != null ? new WareHouseID((UUIDBasedEntityID) locationSpecificId) : null)
                        .build();
                entityType = ReleaseReport.class;
                break;
            case Consts.REPORT_WH_REPLENISHMENT:
                if (sortBy != null)
                sortCriteria = ReplenishmentReportSortCriteria.builder()
                        .sortBy(List.of(ReplenishmentReportSortCriteria.SortBy.valueOf(sortBy)))
                        .sortType(List.of( descending ? ReportSortCriteria.SortType.DESCENDING : ReportSortCriteria.SortType.ASCENDING))
                        .build();
                filterCriteria = ReplenishmentReportFilterCriteria
                        .builder()
                        .warehouseId(locationSpecificId != null ? new WareHouseID((UUIDBasedEntityID) locationSpecificId) : null)
                        .build();
                entityType = ReplenishmentReport.class;
                break;
            case Consts.REPORT_WH_SHIPMENT:
                if (sortBy != null)
                sortCriteria = ShipmentReportSortCriteria.builder()
                        .sortBy(List.of(ShipmentReportSortCriteria.SortBy.valueOf(sortBy)))
                        .sortType(List.of( descending ? ReportSortCriteria.SortType.DESCENDING : ReportSortCriteria.SortType.ASCENDING))
                        .build();
                filterCriteria = ShipmentReportFilterCriteria
                        .builder()
                        .warehouseId(locationSpecificId != null ? new WareHouseID((UUIDBasedEntityID) locationSpecificId) : null)
                        .build();
                entityType = ShipmentReport.class;
                break;
            case Consts.REPORT_WH_WORKSHIFT_REPLENISHMENT:
                if (sortBy != null)
                sortCriteria = WorkShiftReplenishmentReportSortCriteria.builder()
                        .sortBy(List.of(WorkShiftReplenishmentReportSortCriteria.SortBy.valueOf(sortBy)))
                        .sortType(List.of( descending ? ReportSortCriteria.SortType.DESCENDING : ReportSortCriteria.SortType.ASCENDING))
                        .build();
                filterCriteria = WorkShiftReplenishmentReportFilterCriteria
                        .builder()
                        .warehouseId(locationSpecificId != null ? new WareHouseID((UUIDBasedEntityID) locationSpecificId) : null)
                        .build();
                entityType = WorkShiftReplenishmentReport.class;
                break;
            case Consts.REPORT_FS_SUPPLY_REQUIREMENT:
                if (sortBy != null)
                sortCriteria = SupplyRequirementReportSortCriteria.builder()
                        .sortBy(List.of(SupplyRequirementReportSortCriteria.SortBy.valueOf(sortBy)))
                        .sortType(List.of( descending ? ReportSortCriteria.SortType.DESCENDING : ReportSortCriteria.SortType.ASCENDING))
                        .build();
                filterCriteria = SupplyRequirementReportFilterCriteria
                        .builder()
                        .factorySiteId(locationSpecificId != null ? new FactorySiteID((UUIDBasedEntityID) locationSpecificId) : null)
                        .build();
                entityType = SupplyRequirementReport.class;
                break;
            case Consts.REPORT_FS_WORKSHIFT:
                if (sortBy != null)
                sortCriteria = WorkShiftReportSortCriteria.builder()
                        .sortBy(List.of(WorkShiftReportSortCriteria.SortBy.valueOf(sortBy)))
                        .sortType(List.of( descending ? ReportSortCriteria.SortType.DESCENDING : ReportSortCriteria.SortType.ASCENDING))
                        .build();
                filterCriteria = WorkShiftReportFilterCriteria
                        .builder()
                        .factorySiteId(locationSpecificId != null ? new FactorySiteID((UUIDBasedEntityID) locationSpecificId) : null)
                        .build();
                entityType = WorkShiftReport.class;
                break;
            case Consts.REPORT_WAREHOUSE:
//                if (sortBy != null)
//                    sortCriteria = WareReportSortCriteria.builder()
//                            .sortBy(List.of(WorkShiftReportSortCriteria.SortBy.valueOf(sortBy)))
//                            .sortType(List.of( descending ? ReportSortCriteria.SortType.DESCENDING : ReportSortCriteria.SortType.ASCENDING))
//                            .build();
                filterCriteria = WareHouseReportFilterCriteria.builder().warehouseId(locationSpecificId != null ? new WareHouseID((UUIDBasedEntityID) locationSpecificId) : null).build();
                entityType = WareHouseReport.class;
                break;
            case Consts.REPORT_FACTORYSITE:
//                if (sortBy != null)
//                    sortCriteria = WorkShiftReportSortCriteria.builder()
//                            .sortBy(List.of(WorkShiftReportSortCriteria.SortBy.valueOf(sortBy)))
//                            .sortType(List.of( descending ? ReportSortCriteria.SortType.DESCENDING : ReportSortCriteria.SortType.ASCENDING))
//                            .build();
                filterCriteria = FactorySiteReportFilterCriteria.builder().factorySiteId(locationSpecificId != null ? new FactorySiteID((UUIDBasedEntityID) locationSpecificId) : null).build();
                entityType = FactorySiteReport.class;
                break;
            default:
                throw new IllegalArgumentException("Unknown report type: " + typeCriteria);
        }
        else{
            if (sortBy != null)
            sortCriteria = ReportSortCriteria.builder()
                    .sortBy(List.of(ReportSortCriteria.SortBy.valueOf(sortBy)))
                    .sortType(List.of( descending ? ReportSortCriteria.SortType.DESCENDING : ReportSortCriteria.SortType.ASCENDING))
                    .build();
            filterCriteria = ReportFilterCriteria.builder().departmentId(locationSpecificId != null ? new DepartmentID((UUIDBasedEntityID) locationSpecificId) : null).build();
            entityType = Report.class;
        }
        System.out.println("fetchReportEntityType: " + entityType.getName());
        System.out.println("typeCriteriaFromParameters: " + typeCriteria);
        return new Query(entityType, filterCriteria, sortCriteria, pagingCriteria);
    }


    @SneakyThrows
    @Override
    public ReportDetails createReport(CreateReportCmd build) {
        build.validate().onFailedThrow();
        Report report = reportFactory.from(build);

        reportRepository.append(report);
        report.initAcl(aclService);
        messagePublisher.publish(report.edjectEvents());
        return reportDomainToDTOMapper.toReportDetails(report);
    }

    @SneakyThrows
    @Override
    public void updateReport(UpdateReportCmd build) {
        build.validate().onFailedThrow();
        Optional<Report> report = reportRepository.findById(build.getReportId());
        report.orElseThrow(() -> new RuntimeException(String.format("There are no user with id %s", build.getReportId().toString())));

        switch (build) {
            case UpdateSupplyRequirementReportCmd cmd:
                reportModifier.updateBy(((SupplyRequirementReport) report.get()), cmd);
                break;
            case UpdateInventarisationReportCmd cmd:
                reportModifier.updateBy(((InventarisationReport) report.get()), cmd);
                break;
            case UpdateShipmentReportCmd cmd:
                reportModifier.updateBy(((ShipmentReport) report.get()), cmd);
                break;
            case UpdateReleaseReportCmd cmd:
                reportModifier.updateBy(((ReleaseReport) report.get()), cmd);
                break;
            case UpdateReplenishmentReportCmd cmd:
                reportModifier.updateBy(((ReplenishmentReport) report.get()), cmd);
                break;
            case UpdateWorkShiftReplenishmentReportCmd cmd:
                reportModifier.updateBy(((WorkShiftReplenishmentReport) report.get()), cmd);
                break;
            case UpdateWorkShiftReportCmd cmd:
                reportModifier.updateBy(((WorkShiftReport) report.get()), cmd);
                break;
            default:
                throw new Exception("Fake CreateReportCmd subtype");
        }
        reportRepository.update(report.get());
        messagePublisher.publish(report.get().edjectEvents());
    }
    @Override
    public List<ReportDetails> fetch(ReportID key, Integer count, boolean descending, String sortBy, String typeCriteria, EntityID locationSpecificId) {
        Query query = toQuery(
                key, count, descending, sortBy, typeCriteria, locationSpecificId
        );
        return reportDomainToDTOMapper.toReportDetails(reportRepository.findAllWithQuery(query));
    }

    @Override
    public List<ReportDetails> fetchAll() {
        return reportDomainToDTOMapper.toReportDetails(reportRepository.findAll());
    }

    @Override
    public Optional<ReportDetails> fetchById(ReportID id) {
        Optional<Report> report = reportRepository.findById(id);
        return (report.map(reportDomainToDTOMapper::toReportDetails));
    }
}

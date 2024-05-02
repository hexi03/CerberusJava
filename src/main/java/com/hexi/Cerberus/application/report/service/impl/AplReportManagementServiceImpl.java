package com.hexi.Cerberus.application.report.service.impl;

import com.hexi.Cerberus.adapter.web.rest.Consts;
import com.hexi.Cerberus.application.report.service.DTO.details.ReportDetails;
import com.hexi.Cerberus.application.report.service.ReportDomainToDTOMapper;
import com.hexi.Cerberus.application.report.service.ReportManagementService;
import com.hexi.Cerberus.domain.report.Report;
import com.hexi.Cerberus.domain.report.ReportFactory;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.domain.report.ReportModifier;
import com.hexi.Cerberus.domain.report.command.create.CreateReportCmd;
import com.hexi.Cerberus.domain.report.command.update.*;
import com.hexi.Cerberus.domain.report.factorysite.SupplyRequirementReport;
import com.hexi.Cerberus.domain.report.factorysite.WorkShiftReport;
import com.hexi.Cerberus.domain.report.query.filter.*;
import com.hexi.Cerberus.domain.report.query.sort.*;
import com.hexi.Cerberus.domain.report.repository.ReportRepository;
import com.hexi.Cerberus.domain.report.warehouse.*;
import com.hexi.Cerberus.infrastructure.entity.EntityId;
import com.hexi.Cerberus.infrastructure.messaging.MessagePublisher;
import com.hexi.Cerberus.infrastructure.query.PagingCriteria;
import com.hexi.Cerberus.infrastructure.query.Query;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class AplReportManagementServiceImpl implements ReportManagementService {
    public final ReportRepository reportRepository;
    public final MessagePublisher messagePublisher;
    public final MutableAclService aclService;
    public final ReportFactory reportFactory;
    public final ReportModifier reportModifier;
    public final ReportDomainToDTOMapper reportDomainToDTOMapper;

    static Query toQuery(
            @PathVariable(required = false) EntityId key,
            @PathVariable(required = false) Integer count,
            @PathVariable(required = false) Boolean descending,
            @PathVariable(required = false) String sortBy,
            @PathVariable(required = false) String typeCriteria
    ) {
        // Определение PagingCriteria
        PagingCriteria pagingCriteria = new PagingCriteria(
                key, count
        );

        // Определение типа запроса
        ReportSortCriteria sortCriteria = null;
        ReportFilterCriteria filterCriteria = null;
        Class<? extends Report> entityType = null;

        switch (typeCriteria) {
            case Consts.REPORT_WH_INVENTARISATION:
                sortCriteria = InventarisationReportSortCriteria.builder()
                        .sortBy(List.of(InventarisationReportSortCriteria.SortBy.valueOf(sortBy)))
                        .sortType(List.of(ReportSortCriteria.SortType.DESCENDING))
                        .build();
                filterCriteria = InventarisationReportFilterCriteria.builder().build();
                entityType = InventarisationReport.class;
                break;
            case Consts.REPORT_WH_RELEASE:
                sortCriteria = ReleaseReportSortCriteria.builder()
                        .sortBy(List.of(ReleaseReportSortCriteria.SortBy.valueOf(sortBy)))
                        .sortType(List.of(ReportSortCriteria.SortType.DESCENDING))
                        .build();
                filterCriteria = ReleaseReportFilterCriteria.builder().build();
                entityType = ReleaseReport.class;
                break;
            case Consts.REPORT_WH_REPLENISHMENT:
                sortCriteria = ReplenishmentReportSortCriteria.builder()
                        .sortBy(List.of(ReplenishmentReportSortCriteria.SortBy.valueOf(sortBy)))
                        .sortType(List.of(ReportSortCriteria.SortType.DESCENDING))
                        .build();
                filterCriteria = ReplenishmentReportFilterCriteria.builder().build();
                entityType = ReplenishmentReport.class;
                break;
            case Consts.REPORT_WH_SHIPMENT:
                sortCriteria = ShipmentReportSortCriteria.builder()
                        .sortBy(List.of(ShipmentReportSortCriteria.SortBy.valueOf(sortBy)))
                        .sortType(List.of(ReportSortCriteria.SortType.DESCENDING))
                        .build();
                filterCriteria = ShipmentReportFilterCriteria.builder().build();
                entityType = ShipmentReport.class;
                break;
            case Consts.REPORT_WH_WORKSHIFT_REPLENISHMENT:
                sortCriteria = WorkShiftReplenishmentReportSortCriteria.builder()
                        .sortBy(List.of(WorkShiftReplenishmentReportSortCriteria.SortBy.valueOf(sortBy)))
                        .sortType(List.of(ReportSortCriteria.SortType.DESCENDING))
                        .build();
                filterCriteria = WorkShiftReplenishmentReportFilterCriteria.builder().build();
                entityType = WorkShiftReplenishmentReport.class;
                break;
            case Consts.REPORT_FS_SUPPLY_REQUIREMENT:
                sortCriteria = SupplyRequirementReportSortCriteria.builder()
                        .sortBy(List.of(SupplyRequirementReportSortCriteria.SortBy.valueOf(sortBy)))
                        .sortType(List.of(ReportSortCriteria.SortType.DESCENDING))
                        .build();
                filterCriteria = SupplyRequirementReportFilterCriteria.builder().build();
                entityType = SupplyRequirementReport.class;
                break;
            case Consts.REPORT_FS_WORKSHIFT:
                sortCriteria = WorkShiftReportSortCriteria.builder()
                        .sortBy(List.of(WorkShiftReportSortCriteria.SortBy.valueOf(sortBy)))
                        .sortType(List.of(ReportSortCriteria.SortType.DESCENDING))
                        .build();
                filterCriteria = WorkShiftReportFilterCriteria.builder().build();
                entityType = WorkShiftReport.class;
                break;
            default:
                throw new IllegalArgumentException("Unknown report type: " + typeCriteria);
        }

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
        Optional<Report> report = reportRepository.findById(build.getReportID());
        report.orElseThrow(() -> new RuntimeException(String.format("There are no user with id %s", build.getReportID().toString())));

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
    public List<ReportDetails> fetch(ReportID key, Integer count, Boolean descending, String sortBy, String typeCriteria) {
        Query query = toQuery(
                key, count, descending, sortBy, typeCriteria
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

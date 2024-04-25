package com.hexi.Cerberus.application.report.service.impl;

import com.hexi.Cerberus.application.report.service.ReportManagementService;
import com.hexi.Cerberus.domain.report.Report;
import com.hexi.Cerberus.domain.report.ReportFactory;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.domain.report.ReportModifier;
import com.hexi.Cerberus.domain.report.command.create.CreateReportCmd;
import com.hexi.Cerberus.domain.report.command.update.*;
import com.hexi.Cerberus.domain.report.factorysite.SupplyRequirementReport;
import com.hexi.Cerberus.domain.report.factorysite.WorkShiftReport;
import com.hexi.Cerberus.domain.report.repository.ReportRepository;
import com.hexi.Cerberus.domain.report.warehouse.*;
import com.hexi.Cerberus.infrastructure.messaging.MessagePublisher;
import com.hexi.Cerberus.infrastructure.query.Query;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AplReportManagementServiceImpl implements ReportManagementService {
    public final ReportRepository reportRepository;
    public final MessagePublisher messagePublisher;
    public final MutableAclService aclService;
    public final ReportFactory reportFactory;
    public final ReportModifier reportModifier;

    @SneakyThrows
    @Override
    public Report createReport(CreateReportCmd build) {
        build.validate().onFailedThrow();
        Report report = reportFactory.from(build);

        reportRepository.append(report);
        report.initAcl(aclService);
        messagePublisher.publish(report.edjectEvents());
        return report;
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
    public List<Report> fetch(Query query) {
        return reportRepository.findAllWithQuery(query);
    }

    @Override
    public List<Report> fetchAll() {
        return reportRepository.findAll();
    }

    @Override
    public Optional<Report> fetchById(ReportID id) {
        return reportRepository.findById(id);
    }
}

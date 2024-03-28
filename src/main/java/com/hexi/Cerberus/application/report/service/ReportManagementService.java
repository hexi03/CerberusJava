package com.hexi.Cerberus.application.report.service;

import com.hexi.Cerberus.domain.report.Report;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.domain.report.command.create.CreateReportCmd;
import com.hexi.Cerberus.domain.report.command.create.CreateSupplyRequirementReportCmd;
import com.hexi.Cerberus.domain.report.command.update.UpdateReportCmd;
import com.hexi.Cerberus.domain.report.command.update.UpdateSupplyRequirementReportCmd;
import com.hexi.Cerberus.infrastructure.query.Query;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ReportManagementService {
    Report createReport(CreateReportCmd build);

    void updateReport(UpdateReportCmd build);

    List<Report> fetch(Query query);

    List<Report> fetchAll();

    Optional<Report> fetchById(ReportID id);
}

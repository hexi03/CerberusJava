package com.hexi.Cerberus.application.report.service;

import com.hexi.Cerberus.application.report.service.DTO.details.ReportDetails;
import com.hexi.Cerberus.domain.report.Report;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.domain.report.command.create.CreateReportCmd;
import com.hexi.Cerberus.domain.report.command.update.UpdateReportCmd;
import com.hexi.Cerberus.infrastructure.query.Query;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
@Transactional
public interface ReportManagementService {
    ReportDetails createReport(CreateReportCmd build);

    void updateReport(UpdateReportCmd build);

    //List<Report> fetch(Query query);

    List<ReportDetails> fetchAll();

    Optional<ReportDetails> fetchById(ReportID id);

    List<ReportDetails> fetch(ReportID key, Integer count, Boolean descending, String sortBy, String typeCriteria);
}

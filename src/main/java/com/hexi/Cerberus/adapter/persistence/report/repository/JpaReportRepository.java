package com.hexi.Cerberus.adapter.persistence.report.repository;

import com.hexi.Cerberus.adapter.persistence.report.base.ReportModel;
import com.hexi.Cerberus.domain.report.Report;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.domain.report.repository.ReportRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaReportRepository<T extends ReportModel, ID extends ReportID> extends ReportRepository<T, ID>, JpaRepository<T, ID> {
}

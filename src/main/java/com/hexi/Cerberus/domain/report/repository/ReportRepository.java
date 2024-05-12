package com.hexi.Cerberus.domain.report.repository;

import com.hexi.Cerberus.domain.factorysite.FactorySite;
import com.hexi.Cerberus.domain.report.Report;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.domain.report.factorysite.WorkShiftReport;
import com.hexi.Cerberus.infrastructure.repository.Repository;

import java.util.List;

public interface ReportRepository<T extends Report, ID extends ReportID> extends Repository<T, ID> {

}

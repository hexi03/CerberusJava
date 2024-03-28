package com.hexi.Cerberus.adapter.web.rest.Report;

import com.hexi.Cerberus.adapter.web.rest.Report.DTO.details.ReportDetails;
import com.hexi.Cerberus.domain.report.Report;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface DomainToDTOMapper {

    List<ReportDetails> toReportDetails(List<Report> reports);
    ReportDetails toReportDetails(Report report);

}

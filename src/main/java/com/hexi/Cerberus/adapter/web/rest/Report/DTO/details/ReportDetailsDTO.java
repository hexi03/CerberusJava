package com.hexi.Cerberus.adapter.web.rest.Report.DTO.details;

import com.hexi.Cerberus.domain.department.Department;
import com.hexi.Cerberus.domain.report.ReportID;

import java.util.Date;
import java.util.Optional;

public abstract class ReportDetailsDTO implements ReportDetails{
    ReportID id;
    Date createdAt;
    Optional<Date> deletedAt;
}

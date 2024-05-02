package com.hexi.Cerberus.application.report.service.DTO.details;

import com.hexi.Cerberus.domain.department.Department;
import com.hexi.Cerberus.domain.report.ReportID;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.Optional;

@SuperBuilder
public abstract class ReportDetailsDTO implements ReportDetails {
    ReportID id;
    Date createdAt;
    Optional<Date> deletedAt;

    @Override
    public ReportID getId() {
        return id;
    }
}

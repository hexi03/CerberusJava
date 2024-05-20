package com.hexi.Cerberus.application.report.service.DTO.details;

import com.hexi.Cerberus.domain.department.Department;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.domain.user.UserID;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.Optional;

//@Data commented to disable createdAt and deletedAt temporally
@SuperBuilder
@Getter
public abstract class ReportDetailsDTO implements ReportDetails {
    ReportID id;
    UserID creatorId;
    Date createdAt;
    Optional<Date> deletedAt;

    @Override
    public ReportID getId() {
        return id;
    }
}

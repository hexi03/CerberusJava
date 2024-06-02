package com.hexi.Cerberus.domain.report.query.filter;

import com.hexi.Cerberus.domain.department.DepartmentID;
import com.hexi.Cerberus.infrastructure.query.FilterCriteria;
import com.hexi.Cerberus.infrastructure.query.comparation.ComparisonContainer;
import com.hexi.Cerberus.infrastructure.query.comparation.ComparisonUnit;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@SuperBuilder
@Getter
public class ReportFilterCriteria extends FilterCriteria {
    ComparisonUnit<Date> createdDate;
    Boolean isDeleted;
    ComparisonContainer<Date> deletedDate;
//    ReportStatus status;
    DepartmentID departmentId;

}

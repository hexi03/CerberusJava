package com.hexi.Cerberus.domain.report.query.filter;

import com.hexi.Cerberus.domain.department.DepartmentID;
import com.hexi.Cerberus.infrastructure.query.FilterCriteria;
import com.hexi.Cerberus.infrastructure.query.comparation.*;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@SuperBuilder
@Getter
public class ReportFilterCriteria extends FilterCriteria {
    ComparisonContainer<Date> createdDate;
    ComparisonContainer<Date> deletedDate;
    ReportStatus status;
    DepartmentID department;

}

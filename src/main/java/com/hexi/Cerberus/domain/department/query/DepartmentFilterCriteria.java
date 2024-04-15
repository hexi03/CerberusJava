package com.hexi.Cerberus.domain.department.query;

import com.hexi.Cerberus.infrastructure.query.FilterCriteria;
import com.hexi.Cerberus.infrastructure.query.Query;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class DepartmentFilterCriteria extends FilterCriteria {
    String name;

}

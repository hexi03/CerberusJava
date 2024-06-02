package com.hexi.Cerberus.domain.department.query;

import com.hexi.Cerberus.infrastructure.query.FilterCriteria;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Optional;

@SuperBuilder
@Getter
public class DepartmentFilterCriteria extends FilterCriteria {
    Optional<String> name;

}

package com.hexi.Cerberus.adapter.web.rest.FactorySite.DTO;

import com.hexi.Cerberus.domain.department.DepartmentID;
import lombok.Data;

@Data
public class FactorySiteCreateDTO {
    DepartmentID departmentId;
    String name;
}

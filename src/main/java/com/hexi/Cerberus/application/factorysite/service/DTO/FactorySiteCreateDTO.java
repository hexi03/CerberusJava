package com.hexi.Cerberus.application.factorysite.service.DTO;

import com.hexi.Cerberus.domain.department.DepartmentID;
import lombok.Data;

@Data
public class FactorySiteCreateDTO {
    DepartmentID departmentId;
    String name;
}

package com.hexi.Cerberus.application.warehouse.service.DTO;

import com.hexi.Cerberus.domain.department.DepartmentID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class WareHouseCreateDTO {
    DepartmentID departmentId;
    String name;
}

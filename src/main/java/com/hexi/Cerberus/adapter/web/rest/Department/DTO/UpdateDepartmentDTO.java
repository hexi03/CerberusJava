package com.hexi.Cerberus.adapter.web.rest.Department.DTO;

import com.hexi.Cerberus.domain.department.DepartmentID;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateDepartmentDTO {
    public DepartmentID id;
    public String name;
}

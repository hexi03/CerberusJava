package com.hexi.Cerberus.application.department.service.DTO;

import com.hexi.Cerberus.domain.department.DepartmentID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDepartmentDTO {
    public DepartmentID id;
    public String name;
}

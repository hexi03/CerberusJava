package com.hexi.Cerberus.adapter.web.rest.Department.DTO;

import com.hexi.Cerberus.domain.department.DepartmentID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class CreateDepartmentDTO {
    public String name;
}

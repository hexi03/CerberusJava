package com.hexi.Cerberus.adapter.web.rest.WareHouse.DTO;

import com.hexi.Cerberus.domain.department.DepartmentID;
import lombok.Data;

@Data
public class WareHouseCreateDTO {
    DepartmentID departmentId;
    String name;
}

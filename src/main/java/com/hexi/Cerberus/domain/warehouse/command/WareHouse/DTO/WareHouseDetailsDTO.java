package com.hexi.Cerberus.domain.warehouse.command.WareHouse.DTO;

import com.hexi.Cerberus.domain.department.DepartmentID;
import com.hexi.Cerberus.domain.factorysite.FactorySiteID;
import lombok.Data;

@Data
public class WareHouseDetailsDTO {
    FactorySiteID id;
    DepartmentID departmentId;
    String name;

}

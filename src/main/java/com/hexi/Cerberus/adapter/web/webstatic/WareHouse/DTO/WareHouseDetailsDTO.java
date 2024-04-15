package com.hexi.Cerberus.adapter.web.webstatic.WareHouse.DTO;

import com.hexi.Cerberus.domain.department.DepartmentID;
import com.hexi.Cerberus.domain.factorysite.FactorySiteID;
import com.hexi.Cerberus.domain.warehouse.WareHouseID;
import lombok.Data;

@Data
public class WareHouseDetailsDTO {
    WareHouseID id;
    DepartmentID departmentId;
    String name;

}

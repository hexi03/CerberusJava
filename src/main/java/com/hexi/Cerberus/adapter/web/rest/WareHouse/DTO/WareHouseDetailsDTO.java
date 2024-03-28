package com.hexi.Cerberus.adapter.web.rest.WareHouse.DTO;

import com.hexi.Cerberus.domain.department.DepartmentID;
import com.hexi.Cerberus.domain.factorysite.FactorySiteID;
import com.hexi.Cerberus.domain.warehouse.WareHouseID;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class WareHouseDetailsDTO {
    WareHouseID id;
    DepartmentID departmentId;
    String name;

}

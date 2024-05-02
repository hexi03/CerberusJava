package com.hexi.Cerberus.application.warehouse.service.DTO;

import com.hexi.Cerberus.domain.department.DepartmentID;
import com.hexi.Cerberus.domain.warehouse.WareHouseID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WareHouseDetailsDTO {
    WareHouseID id;
    DepartmentID departmentId;
    String name;

}

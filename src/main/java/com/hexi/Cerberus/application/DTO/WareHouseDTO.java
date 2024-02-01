package com.hexi.Cerberus.application.DTO;

import com.hexi.Cerberus.domain.commontypes.DepartmentID;
import com.hexi.Cerberus.domain.commontypes.WareHouseID;
import lombok.Data;

@Data
public class WareHouseDTO {
    WareHouseID id;
    DepartmentID departmentId;
    String name;
}

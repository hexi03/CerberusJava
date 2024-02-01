package com.hexi.Cerberus.domain.entities;

import com.hexi.Cerberus.domain.commontypes.DepartmentID;
import com.hexi.Cerberus.domain.commontypes.WareHouseID;
import lombok.Data;

@Data
public class WareHouse {
    WareHouseID id;
    DepartmentID departmentId;
    String name;
}

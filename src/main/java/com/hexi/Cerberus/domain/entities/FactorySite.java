package com.hexi.Cerberus.domain.entities;

import com.hexi.Cerberus.domain.commontypes.DepartmentID;
import com.hexi.Cerberus.domain.commontypes.FactorySiteID;
import lombok.Data;

@Data
public class FactorySite {
    FactorySiteID id;
    DepartmentID departmentId;
    String name;
}

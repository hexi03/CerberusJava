package com.hexi.Cerberus.application.DTO;

import com.hexi.Cerberus.domain.commontypes.DepartmentID;
import com.hexi.Cerberus.domain.commontypes.FactorySiteID;
import lombok.Data;

@Data
public class FactorySiteDTO {
    FactorySiteID id;
    DepartmentID departmentId;
    String name;

}

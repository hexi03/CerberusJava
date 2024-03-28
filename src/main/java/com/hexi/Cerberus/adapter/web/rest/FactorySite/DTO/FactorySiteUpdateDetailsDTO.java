package com.hexi.Cerberus.adapter.web.rest.FactorySite.DTO;

import com.hexi.Cerberus.domain.department.DepartmentID;
import com.hexi.Cerberus.domain.factorysite.FactorySiteID;
import lombok.Data;


@Data
public class FactorySiteUpdateDetailsDTO {
    FactorySiteID id;
    String name;
}

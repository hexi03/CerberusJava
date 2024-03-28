package com.hexi.Cerberus.adapter.web.rest.Department.DTO;

import com.hexi.Cerberus.domain.department.DepartmentID;
import com.hexi.Cerberus.domain.factorysite.FactorySite;
import com.hexi.Cerberus.domain.factorysite.FactorySiteID;
import com.hexi.Cerberus.domain.warehouse.WareHouseID;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DepartmentDetailsDTO {
    public DepartmentID id;
    public String name;
    List<WareHouseID> wareHouses;
    List<FactorySiteID> factorySites;
}

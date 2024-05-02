package com.hexi.Cerberus.application.factorysite.service.DTO;

import com.hexi.Cerberus.domain.factorysite.FactorySiteID;
import com.hexi.Cerberus.domain.warehouse.WareHouseID;
import lombok.Data;

import java.util.List;

@Data
public class FactorySiteUpdateSupplyDTO {
    FactorySiteID id;
    List<WareHouseID> suppliers;
}

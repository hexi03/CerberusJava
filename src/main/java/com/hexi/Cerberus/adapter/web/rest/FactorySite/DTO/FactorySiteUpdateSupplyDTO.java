package com.hexi.Cerberus.adapter.web.rest.FactorySite.DTO;

import com.hexi.Cerberus.domain.factorysite.FactorySiteID;
import com.hexi.Cerberus.domain.warehouse.WareHouseID;
import lombok.Data;

import java.util.List;
@Data
public class FactorySiteUpdateSupplyDTO {
    FactorySiteID id;
    List<WareHouseID> suppliers;
}

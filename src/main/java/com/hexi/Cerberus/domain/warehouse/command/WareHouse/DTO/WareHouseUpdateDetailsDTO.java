package com.hexi.Cerberus.domain.warehouse.command.WareHouse.DTO;

import com.hexi.Cerberus.domain.factorysite.FactorySiteID;
import com.hexi.Cerberus.domain.warehouse.WareHouseID;
import lombok.Data;


@Data
public class WareHouseUpdateDetailsDTO {
    WareHouseID id;
    String name;
}

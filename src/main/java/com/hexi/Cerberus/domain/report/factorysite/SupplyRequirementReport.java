package com.hexi.Cerberus.domain.report.factorysite;

import com.hexi.Cerberus.application.report.service.DTO.details.ReportDetails;
import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.warehouse.WareHouse;

import java.util.Map;

public interface SupplyRequirementReport extends FactorySiteReport {

    void setTargetWareHouse(WareHouse wareHouse);

    Map<Item,Integer> getRequirements();

    void setRequirements(Map<Item, Integer> reqMap);

    WareHouse getTargetWareHouse();
}

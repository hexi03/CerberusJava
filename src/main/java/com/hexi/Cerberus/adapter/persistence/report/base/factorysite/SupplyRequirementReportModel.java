package com.hexi.Cerberus.adapter.persistence.report.base.factorysite;

import com.hexi.Cerberus.adapter.persistence.factorysite.base.FactorySiteModel;
import com.hexi.Cerberus.adapter.persistence.item.base.ItemModel;
import com.hexi.Cerberus.adapter.persistence.warehouse.base.WareHouseModel;
import com.hexi.Cerberus.domain.factorysite.FactorySite;
import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.domain.report.factorysite.SupplyRequirementReport;
import com.hexi.Cerberus.domain.warehouse.WareHouse;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


public class SupplyRequirementReportModel extends FactorySiteReportModel implements SupplyRequirementReport {
    WareHouseModel targetWareHouse;
    Map<ItemModel, Integer> requirements = new HashMap<>();

    public SupplyRequirementReportModel(
            ReportID id,
            FactorySiteModel factorySite,
            Date createdAt,
            Date expirationDate,
            Optional<Date> deletedAt,
            WareHouseModel targetWareHouse,
            Map<ItemModel, Integer> requirements) {
        super(id, factorySite, createdAt, expirationDate, deletedAt);
        this.requirements = requirements;
        this.targetWareHouse = targetWareHouse;
    }

    public SupplyRequirementReportModel(
            ReportID id,
            FactorySiteModel factorySite,
            Date createdAt,
            Date expirationDate,
            WareHouseModel targetWareHouse,
            Map<ItemModel, Integer> requirements) {
        super(id, factorySite, createdAt, expirationDate);
        this.requirements = requirements;
        this.targetWareHouse = targetWareHouse;
    }


//    public SupplyRequirementReportModel(
//            FactorySite factorySite,
//            Date createdAt,
//            Date expirationDate,
//            WareHouse targetWareHouse,
//            Map<Item, Integer> requirements) {
//        super(factorySite, createdAt, expirationDate);
//        this.requirements = requirements;
//        this.targetWareHouse = targetWareHouse;
//    }

    private SupplyRequirementReportModel() {
        super();
    }

    @Override
    public void setTargetWareHouse(WareHouse wareHouse) {
        this.targetWareHouse = (WareHouseModel) wareHouse;
    }

    @Override
    public void setRequirements(Map<Item, Integer> reqMap) {
        this.requirements = reqMap
                .entrySet()
                .stream()
                .collect(Collectors.toMap(entry -> (ItemModel) entry.getKey(), entry -> entry.getValue()));
    }

    @Override
    public Map<Item, Integer> getItems() {
        return null;
    }
}

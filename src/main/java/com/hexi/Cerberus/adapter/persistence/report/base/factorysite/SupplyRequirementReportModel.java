package com.hexi.Cerberus.adapter.persistence.report.base.factorysite;

import com.hexi.Cerberus.adapter.persistence.factorysite.base.FactorySiteModel;
import com.hexi.Cerberus.adapter.persistence.item.base.ItemEntry;
import com.hexi.Cerberus.adapter.persistence.item.base.ItemModel;
import com.hexi.Cerberus.adapter.persistence.user.base.UserModel;
import com.hexi.Cerberus.adapter.persistence.warehouse.base.WareHouseModel;
import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.domain.report.factorysite.SupplyRequirementReport;
import com.hexi.Cerberus.domain.warehouse.WareHouse;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Access(AccessType.FIELD)
public class SupplyRequirementReportModel extends FactorySiteReportModel implements SupplyRequirementReport {
    @ManyToMany(cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinTable(name = "supply_requirement_report_target_warehouse_assoc")
    List<WareHouseModel> targetWareHouses;
    @ManyToMany(cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinTable(name = "supply_requirement_report_requirements_item_entry_assoc")
    Collection<ItemEntry> requirements = new ArrayList<>();

    public SupplyRequirementReportModel(
            ReportID id,
            FactorySiteModel factorySite,
            Date createdAt,
            Date expirationDate,
            Optional<Date> deletedAt,
            List<WareHouseModel> targetWareHouses,
            Map<ItemModel, Integer> requirements, UserModel creator) {
        super(id, factorySite, createdAt, expirationDate, deletedAt, creator);
        this.requirements = requirements.entrySet().stream().map(entry -> new ItemEntry(entry.getKey(),entry.getValue())).collect(Collectors.toList());
        this.targetWareHouses = targetWareHouses;
    }

    public SupplyRequirementReportModel(
            ReportID id,
            FactorySiteModel factorySite,
            Date createdAt,
            Date expirationDate,
            List<WareHouseModel> targetWareHouses,
            Map<ItemModel, Integer> requirements, UserModel creator) {
        super(id, factorySite, createdAt, expirationDate, creator);
        this.requirements = requirements.entrySet().stream().map(entry -> new ItemEntry(entry.getKey(),entry.getValue())).collect(Collectors.toList());
        this.targetWareHouses = targetWareHouses;
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
    public void setTargetWareHouses(List<WareHouse> wareHouses) {
        this.targetWareHouses = wareHouses.stream().map(wareHouseModel -> (WareHouseModel) wareHouseModel).collect(Collectors.toList());
    }
    @Override
    public Map<Item,Integer> getRequirements(){
        return requirements.stream().collect(Collectors.toMap(itemEntry -> itemEntry.getItem(), itemEntry -> itemEntry.getAmount()));
    }

    @Override
    public void setRequirements(Map<Item, Integer> reqMap) {
        this.requirements = reqMap.entrySet().stream().map(entry -> new ItemEntry((ItemModel)entry.getKey(),entry.getValue())).collect(Collectors.toList());

    }

    @Override
    public List<WareHouse> getTargetWareHouses() {
        return targetWareHouses.stream().map(wareHouseModel -> (WareHouse) wareHouseModel).collect(Collectors.toList());
    }

}

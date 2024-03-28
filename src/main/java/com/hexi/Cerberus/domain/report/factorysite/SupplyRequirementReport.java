package com.hexi.Cerberus.domain.report.factorysite;

import com.hexi.Cerberus.domain.department.Department;
import com.hexi.Cerberus.domain.factorysite.FactorySite;
import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.report.Report;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.domain.report.command.create.CreateSupplyRequirementReportCmd;
import com.hexi.Cerberus.domain.report.command.update.UpdateSupplyRequirementReportCmd;
import com.hexi.Cerberus.domain.warehouse.WareHouse;
import com.hexi.Cerberus.domain.warehouse.WareHouseID;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
@Setter
public class SupplyRequirementReport extends FactorySiteReport{
    WareHouse targetWareHouse;
    Map<Item, Integer> requirements = new HashMap<>();
    public SupplyRequirementReport(
            ReportID id,
            FactorySite factorySite,
            Date createdAt,
            Date expirationDate,
            Optional<Date> deletedAt,
            WareHouse targetWareHouse,
            Map<Item, Integer> requirements) {
        super(id, factorySite, createdAt, expirationDate, deletedAt);
        this.requirements = requirements;
        this.targetWareHouse = targetWareHouse;
    }

    public SupplyRequirementReport(
            FactorySite factorySite,
            Date createdAt,
            Date expirationDate,
            WareHouse targetWareHouse,
            Map<Item, Integer> requirements) {
        super(factorySite, createdAt, expirationDate);
        this.requirements = requirements;
        this.targetWareHouse = targetWareHouse;
    }

    private SupplyRequirementReport(){
        super();
    }

}

package com.hexi.Cerberus.domain.report.warehouse;

import com.hexi.Cerberus.domain.factorysite.FactorySite;
import com.hexi.Cerberus.domain.factorysite.FactorySiteID;
import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.report.Report;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.domain.report.command.create.CreateWorkShiftReplenishmentReportCmd;
import com.hexi.Cerberus.domain.report.command.update.UpdateWorkShiftReplenishmentReportCmd;
import com.hexi.Cerberus.domain.warehouse.WareHouse;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
@Getter
@Setter
public class WorkShiftReplenishmentReport extends WareHouseReport implements ItemReplenish{
    FactorySite factorySiteId;
    Report workShiftReportId;
    Map<Item, Integer> items = new HashMap<>();
    //Невостребованные остатки на возврат
    Map<Item, Integer> unclaimedRemains;
    public WorkShiftReplenishmentReport(
            ReportID id,
            WareHouse wareHouse,
            Date createdAt,
            Date expirationDate,
            Optional<Date> deletedAt,
            FactorySite factorySiteId,
            Report workShiftReportId,
            Map<Item, Integer> items,
            Map<Item, Integer> unclaimedRemains) {
        super(id, wareHouse, createdAt, expirationDate, deletedAt);
        this.items = items;
        this.unclaimedRemains = unclaimedRemains;
        this.factorySiteId = factorySiteId;
        this.workShiftReportId = workShiftReportId;
    }

    public WorkShiftReplenishmentReport(
            WareHouse wareHouse,
            Date createdAt,
            Date expirationDate,
            FactorySite factorySiteId,
            Report workShiftReportId,
            Map<Item, Integer> items,
            Map<Item, Integer> unclaimedRemains) {
        super(wareHouse, createdAt, expirationDate);
        this.workShiftReportId = workShiftReportId;
        this.factorySiteId = factorySiteId;
        this.items = items;
        this.unclaimedRemains = unclaimedRemains;
    }

    private WorkShiftReplenishmentReport(){
        super();
    }

}

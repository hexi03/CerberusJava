package com.hexi.Cerberus.domain.report.factorysite;

import com.hexi.Cerberus.domain.department.Department;
import com.hexi.Cerberus.domain.factorysite.FactorySite;
import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.product.Product;
import com.hexi.Cerberus.domain.product.ProductID;
import com.hexi.Cerberus.domain.report.Report;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.domain.report.command.create.CreateWorkShiftReportCmd;
import com.hexi.Cerberus.domain.report.command.update.UpdateWorkShiftReportCmd;
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
public class WorkShiftReport extends FactorySiteReport{
    WareHouse targetWareHouseId;
    Map<Product, Integer> produced = new HashMap<>();
    Map<Item, Integer> losses = new HashMap<>();
    Map<Item, Integer> remains = new HashMap<>();
    public WorkShiftReport(
            ReportID id,
            FactorySite factorySite,
            Date createdAt,
            Date expirationDate,
            Optional<Date> deletedAt,
            WareHouse targetWareHouseId,
            Map<Product, Integer> produced,
            Map<Item, Integer> losses,
            Map<Item, Integer> remains) {
        super(id, factorySite, createdAt, expirationDate, deletedAt);
        this.targetWareHouseId = targetWareHouseId;
        this.produced = produced;
        this.losses = losses;
        this.remains = remains;
    }

    public WorkShiftReport(
            FactorySite factorySite,
            Date createdAt,
            Date expirationDate,
            WareHouse targetWareHouseId,
            Map<Product, Integer> produced,
            Map<Item, Integer> losses,
            Map<Item, Integer> remains) {
        super(factorySite, createdAt, expirationDate);
        this.targetWareHouseId = targetWareHouseId;
        this.produced = produced;
        this.losses = losses;
        this.remains = remains;
    }

    private WorkShiftReport(){
        super();
    }

}

package com.hexi.Cerberus.domain.report.warehouse;

import com.hexi.Cerberus.domain.department.Department;
import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.report.Report;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.domain.report.command.create.CreateInventarisationReportCmd;
import com.hexi.Cerberus.domain.report.command.update.UpdateInventarisationReportCmd;
import com.hexi.Cerberus.domain.warehouse.WareHouse;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
@Getter
@Setter
public class InventarisationReport extends WareHouseReport {
    Map<Item, Integer> items = new HashMap<>();
    public InventarisationReport(
            ReportID id,
            WareHouse wareHouse,
            Date createdAt,
            Date expirationDate,
            Optional<Date> deletedAt,
            Map<Item, Integer> items) {
        super(id, wareHouse, createdAt, expirationDate, deletedAt);
        this.items = items;
    }

    public InventarisationReport(
            WareHouse wareHouse,
            Date createdAt,
            Date expirationDate,
            Map<Item, Integer> items) {
        super( wareHouse, createdAt, expirationDate);
        this.items = items;
        this.wareHouse = wareHouse;
    }

    private InventarisationReport(){
        super();
    }

}

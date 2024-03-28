package com.hexi.Cerberus.domain.report.warehouse;

import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.report.Report;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.domain.report.command.create.CreateShipmentReportCmd;
import com.hexi.Cerberus.domain.report.command.update.UpdateShipmentReportCmd;
import com.hexi.Cerberus.domain.warehouse.WareHouse;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
@Getter
@Setter
public class ShipmentReport extends WareHouseReport implements ItemRelease{
    Map<Item, Integer> items = new HashMap<>();
    public ShipmentReport(
            ReportID id,
            WareHouse wareHouse,
            Date createdAt,
            Date expirationDate,
            Optional<Date> deletedAt,
            Map<Item, Integer> items) {
        super(id, wareHouse, createdAt, expirationDate, deletedAt);
        this.items = items;
    }

    public ShipmentReport(
            WareHouse wareHouse,
            Date createdAt,
            Date expirationDate,
            Map<Item, Integer> items) {
        super(wareHouse, createdAt, expirationDate);
        this.items = items;
    }

    private ShipmentReport(){
        super();
    }

}

package com.hexi.Cerberus.domain.report.warehouse;

import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.report.Report;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.domain.report.command.create.CreateReleaseReportCmd;
import com.hexi.Cerberus.domain.report.command.update.UpdateReleaseReportCmd;
import com.hexi.Cerberus.domain.warehouse.WareHouse;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
@Getter
@Setter
public class ReleaseReport extends WareHouseReport implements ItemRelease{
    Report supplyReqReportId;
    Map<Item, Integer> items = new HashMap<>();
    public ReleaseReport(
            ReportID id,
            WareHouse wareHouse,
            Date createdAt,
            Date expirationDate,
            Optional<Date> deletedAt,
            Report supplyReqReportId,
            Map<Item, Integer> items) {
        super(id, wareHouse, createdAt, expirationDate, deletedAt);
        this.supplyReqReportId = supplyReqReportId;
        this.items = items;
    }

    public ReleaseReport(
            WareHouse wareHouse,
            Date createdAt,
            Date expirationDate,
            Report supplyReqReportId,
            Map<Item, Integer> items) {
        super(wareHouse, createdAt, expirationDate);
        this.supplyReqReportId = supplyReqReportId;
        this.items = items;
    }

    private ReleaseReport(){
        super();
    }

}

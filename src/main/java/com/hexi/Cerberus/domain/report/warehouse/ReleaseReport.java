package com.hexi.Cerberus.domain.report.warehouse;

import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.report.Report;

import java.util.Map;

public interface ReleaseReport extends WareHouseReport, ItemStorageOperationReport {
    void setSupplyReqReportId(Report report);

    void setItems(Map<Item, Integer> reqMap);
}

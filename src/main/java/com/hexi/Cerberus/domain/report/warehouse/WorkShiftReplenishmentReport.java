package com.hexi.Cerberus.domain.report.warehouse;

import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.report.Report;

import java.util.Map;

public interface WorkShiftReplenishmentReport extends WareHouseReport, ItemStorageOperationReport {
    void setWorkShiftReportId(Report report);

    void setItems(Map<Item, Integer> reqMap);

}

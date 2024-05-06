package com.hexi.Cerberus.domain.report.warehouse;

import com.hexi.Cerberus.application.report.service.DTO.details.ReportDetails;
import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.report.Report;
import com.hexi.Cerberus.domain.report.factorysite.WorkShiftReport;

import java.util.Map;

public interface WorkShiftReplenishmentReport extends WareHouseReport, ItemStorageOperationReport {
    void setWorkShiftReport(WorkShiftReport report);

    void setItems(Map<Item, Integer> reqMap);

    Map<Item, Integer> getUnclaimedRemains();

    WorkShiftReport getWorkShiftReport();
}

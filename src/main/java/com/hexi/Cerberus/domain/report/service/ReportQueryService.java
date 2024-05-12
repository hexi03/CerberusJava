package com.hexi.Cerberus.domain.report.service;

import com.hexi.Cerberus.adapter.persistence.report.base.factorysite.SupplyRequirementReportModel;
import com.hexi.Cerberus.adapter.persistence.report.base.factorysite.WorkShiftReportModel;
import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.domain.warehouse.WareHouse;
import lombok.SneakyThrows;

import java.util.List;
import java.util.Map;

public interface ReportQueryService {

    Map<ReportID, Map<ItemID, Integer>> findUnsatisfiedByReplenishedProducedWorkShiftReports(WareHouse targetWareHouse);

    Map<ReportID,Map<ItemID, Integer>> findUnsatisfiedByReplenishedUnclaimedRemainsWorkShiftReports(WareHouse targetWareHouse);

    Map<ReportID,Map<ItemID, Integer>> findUnsatisfiedSupplyRequirementReports(WareHouse targetWareHouse);
}

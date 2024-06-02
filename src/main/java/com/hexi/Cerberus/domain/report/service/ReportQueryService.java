package com.hexi.Cerberus.domain.report.service;

import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.domain.report.factorysite.SupplyRequirementReport;
import com.hexi.Cerberus.domain.report.factorysite.WorkShiftReport;
import com.hexi.Cerberus.domain.report.warehouse.ReleaseReport;
import com.hexi.Cerberus.domain.report.warehouse.WorkShiftReplenishmentReport;
import com.hexi.Cerberus.domain.warehouse.WareHouse;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

public interface ReportQueryService {

    List<AbstractMap.SimpleImmutableEntry<WorkShiftReport,Map<ItemID, Integer>>>  findUnsatisfiedByReplenishedProducedWorkShiftReports(WareHouse targetWareHouse);

    List<AbstractMap.SimpleImmutableEntry<WorkShiftReport,Map<ItemID, Integer>>>  findUnsatisfiedByReplenishedUnclaimedRemainsWorkShiftReports(WareHouse targetWareHouse);

    List<AbstractMap.SimpleImmutableEntry<SupplyRequirementReport,Map<ItemID, Integer>>>  findUnsatisfiedSupplyRequirementReports(WareHouse targetWareHouse);

    List<ReleaseReport> findRelatedReleaseReports(WareHouse wareHouse, ReportID supReqRepId);

    List<WorkShiftReplenishmentReport> findRelatedWorkShiftReplenishmentReports(WareHouse wareHouse, ReportID wsReplRepId);
}

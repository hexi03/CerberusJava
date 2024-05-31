package com.hexi.Cerberus.domain.report.service;

import com.hexi.Cerberus.adapter.persistence.report.base.factorysite.SupplyRequirementReportModel;
import com.hexi.Cerberus.adapter.persistence.report.base.factorysite.WorkShiftReportModel;
import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.domain.report.factorysite.SupplyRequirementReport;
import com.hexi.Cerberus.domain.report.factorysite.WorkShiftReport;
import com.hexi.Cerberus.domain.warehouse.WareHouse;
import lombok.SneakyThrows;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

public interface ReportQueryService {

    List<AbstractMap.SimpleImmutableEntry<WorkShiftReport,Map<ItemID, Integer>>>  findUnsatisfiedByReplenishedProducedWorkShiftReports(WareHouse targetWareHouse);

    List<AbstractMap.SimpleImmutableEntry<WorkShiftReport,Map<ItemID, Integer>>>  findUnsatisfiedByReplenishedUnclaimedRemainsWorkShiftReports(WareHouse targetWareHouse);

    List<AbstractMap.SimpleImmutableEntry<SupplyRequirementReport,Map<ItemID, Integer>>>  findUnsatisfiedSupplyRequirementReports(WareHouse targetWareHouse);
}

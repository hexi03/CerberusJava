package com.hexi.Cerberus.domain.report.warehouse;

import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.report.Report;
import com.hexi.Cerberus.domain.report.factorysite.SupplyRequirementReport;

import java.util.Map;

public interface ReleaseReport extends WareHouseReport, ItemRelease {
    void setSupplyReqReportId(Report report);

    void setItems(Map<Item, Integer> reqMap);

    SupplyRequirementReport getSupplyReqReport();


    @Override
    default Map<Item, Integer> getSummaryRelease() {return getItems();}
}

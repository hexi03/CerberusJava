package com.hexi.Cerberus.domain.report.warehouse;

import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.report.factorysite.WorkShiftReport;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface WorkShiftReplenishmentReport extends WareHouseReport, ItemReplenish {
    void setWorkShiftReport(WorkShiftReport report);

    void setItems(Map<Item, Integer> reqMap);

    Map<Item, Integer> getUnclaimedRemains();

    WorkShiftReport getWorkShiftReport();

    @Override
    default Map<Item, Integer> getSummaryReplenish(){
        return Stream.concat(getItems().entrySet().stream(), getUnclaimedRemains().entrySet().stream()).collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue(), Integer::sum));
    }

    void setUnclaimedRemains(Map<Item, Integer> unclaimedRemainsMap);
}

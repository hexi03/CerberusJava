package com.hexi.Cerberus.domain.report.warehouse;

import com.hexi.Cerberus.domain.item.Item;

import java.util.Map;

public interface ItemReplenish extends ItemStorageOperationReport {
    Map<Item, Integer> getSummaryReplenish();
}

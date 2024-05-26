package com.hexi.Cerberus.domain.report.warehouse;

import com.hexi.Cerberus.domain.item.Item;

import java.util.Map;

public interface ItemRelease extends ItemStorageOperationReport {
    Map<Item, Integer> getSummaryRelease();
}

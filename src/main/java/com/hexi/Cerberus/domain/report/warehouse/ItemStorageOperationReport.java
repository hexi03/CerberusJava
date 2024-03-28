package com.hexi.Cerberus.domain.report.warehouse;

import com.hexi.Cerberus.domain.item.Item;

import java.util.HashMap;
import java.util.Map;

public interface ItemStorageOperationReport {
    Map<Item, Integer> getItems();
}

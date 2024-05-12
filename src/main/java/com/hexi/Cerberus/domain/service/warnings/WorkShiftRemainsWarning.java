package com.hexi.Cerberus.domain.service.warnings;

import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.infrastructure.StateWarning;

import java.util.Map;

public class WorkShiftRemainsWarning implements StateWarning {
    Map<Item, Integer> remains;
    public WorkShiftRemainsWarning(Map<Item, Integer> remains) {
        this.remains = remains;
    }

    @Override
    public String getMessage() {
        return "WorkShiftRemainsWarning: " + remains.toString();
    }
}

package com.hexi.Cerberus.domain.service.warnings;

import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.infrastructure.StateWarning;

import java.util.Map;

public class WorkShiftLossesWarning implements StateWarning {
    Map<Item, Integer> losses;
    public WorkShiftLossesWarning(Map<Item, Integer> losses) {
        this.losses = losses;
    }

    @Override
    public String getMessage() {
        return "WorkShiftLossesWarning: " + losses.toString();
    }
}

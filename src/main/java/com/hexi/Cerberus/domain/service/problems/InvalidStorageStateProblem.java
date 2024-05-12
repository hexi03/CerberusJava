package com.hexi.Cerberus.domain.service.problems;

import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.infrastructure.StateProblem;

import java.util.Map;

public class InvalidStorageStateProblem implements StateProblem {
    Map<ItemID, Integer> invalidStorageStatePositions;
    public InvalidStorageStateProblem(Map<ItemID, Integer> invalidStorageStatePositions) {
        this.invalidStorageStatePositions = invalidStorageStatePositions;
    }

    @Override
    public String getMessage() {
        return "InvalidStorageStateProblem: " + invalidStorageStatePositions.toString();
    }
}

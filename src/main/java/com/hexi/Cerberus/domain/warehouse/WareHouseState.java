package com.hexi.Cerberus.domain.warehouse;

import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.infrastructure.StateProblem;
import com.hexi.Cerberus.infrastructure.StateWarning;
import lombok.Builder;
import lombok.Singular;

import java.util.List;
import java.util.Map;

@Builder
public class WareHouseState {
    @Singular
    List<StateProblem> problems;
    @Singular
    List<StateWarning> warnings;

    Map<Item, Integer> items;


}
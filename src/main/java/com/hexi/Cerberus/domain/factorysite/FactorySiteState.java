package com.hexi.Cerberus.domain.factorysite;

import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.infrastructure.StateProblem;
import com.hexi.Cerberus.infrastructure.StateWarning;
import lombok.Builder;
import lombok.Singular;

import java.util.List;
@Builder
public class FactorySiteState {
    @Singular
    List<StateProblem> problems;
    @Singular
    List<StateWarning> warnings;


}

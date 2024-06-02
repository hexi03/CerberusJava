package com.hexi.Cerberus.domain.factorysite;

import com.hexi.Cerberus.infrastructure.StateProblem;
import com.hexi.Cerberus.infrastructure.StateWarning;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.List;

@Builder
@Getter
public class FactorySiteState {
    @Singular
    List<StateProblem> problems;
    @Singular
    List<StateWarning> warnings;


}

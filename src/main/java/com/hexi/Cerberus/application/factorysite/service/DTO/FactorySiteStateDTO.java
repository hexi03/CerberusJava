package com.hexi.Cerberus.application.factorysite.service.DTO;

import com.hexi.Cerberus.infrastructure.StateProblem;
import com.hexi.Cerberus.infrastructure.StateWarning;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.List;

@Data
@Builder
public class FactorySiteStateDTO {
    @Singular
    List<String> problems;
    @Singular
    List<String> warnings;
}

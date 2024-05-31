package com.hexi.Cerberus.application.factorysite.service.DTO;

import com.hexi.Cerberus.application.mapper.DTO.StateProblemDTO;
import com.hexi.Cerberus.application.mapper.DTO.StateWarningDTO;
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
    List<StateProblemDTO> problems;
    @Singular
    List<StateWarningDTO> warnings;
}

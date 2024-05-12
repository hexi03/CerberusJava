package com.hexi.Cerberus.application.factorysite.service;

import com.hexi.Cerberus.application.factorysite.service.DTO.FactorySiteStateDTO;
import com.hexi.Cerberus.application.warehouse.service.DTO.WareHouseStateDTO;
import com.hexi.Cerberus.domain.factorysite.FactorySiteState;
import com.hexi.Cerberus.domain.warehouse.WareHouseState;
import org.mapstruct.Mapper;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface FactorySiteStateDomainToDTOMapper {
    default FactorySiteStateDTO mapStateToDTO(FactorySiteState state){
        return FactorySiteStateDTO
                .builder()
                .problems(state.getProblems().stream().map(stateProblem -> stateProblem.getMessage()).collect(Collectors.toList()))
                .warnings(state.getWarnings().stream().map(stateWarning -> stateWarning.getMessage()).collect(Collectors.toList()))
                .build();
    };
}

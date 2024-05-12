package com.hexi.Cerberus.application.warehouse.service;

import com.hexi.Cerberus.application.warehouse.service.DTO.WareHouseStateDTO;
import com.hexi.Cerberus.domain.warehouse.WareHouseState;
import org.mapstruct.Mapper;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface WareHouseStateDomainToDTOMapper {
    default WareHouseStateDTO mapStateToDTO(WareHouseState wareHouseState){
        return WareHouseStateDTO
                .builder()
                .problems(wareHouseState.getProblems().stream().map(stateProblem -> stateProblem.getMessage()).collect(Collectors.toList()))
                .warnings(wareHouseState.getWarnings().stream().map(stateWarning -> stateWarning.getMessage()).collect(Collectors.toList()))
                .items(wareHouseState.getItems().entrySet().stream().collect(Collectors.toMap(t -> t.getKey().getId(), t -> t.getValue())))
                .build();
    };
}

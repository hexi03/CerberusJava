package com.hexi.Cerberus.application.factorysite.service;

import com.hexi.Cerberus.application.factorysite.service.DTO.FactorySiteStateDTO;
import com.hexi.Cerberus.application.mapper.DTO.StateProblemDTO;
import com.hexi.Cerberus.application.mapper.DTO.StateWarningDTO;
import com.hexi.Cerberus.application.mapper.DTO.problems.WorkShiftConsumablesLossProblemDTO;
import com.hexi.Cerberus.application.mapper.DTO.problems.WorkShiftConsumablesTooMuchProblemDTO;
import com.hexi.Cerberus.application.mapper.DTO.warnings.WorkShiftLossesWarningDTO;
import com.hexi.Cerberus.application.mapper.DTO.warnings.WorkShiftRemainsWarningDTO;
import com.hexi.Cerberus.application.warehouse.service.DTO.WareHouseStateDTO;
import com.hexi.Cerberus.domain.factorysite.FactorySiteState;
import com.hexi.Cerberus.domain.service.problems.*;
import com.hexi.Cerberus.domain.service.warnings.UnsatisfiedSupplyRequirementReportWarning;
import com.hexi.Cerberus.domain.service.warnings.UnsatisfiedWorkShiftReportWarning;
import com.hexi.Cerberus.domain.service.warnings.WorkShiftLossesWarning;
import com.hexi.Cerberus.domain.service.warnings.WorkShiftRemainsWarning;
import com.hexi.Cerberus.domain.warehouse.WareHouseState;
import com.hexi.Cerberus.infrastructure.StateProblem;
import com.hexi.Cerberus.infrastructure.StateWarning;
import org.mapstruct.Mapper;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface FactorySiteStateDomainToDTOMapper {
    default FactorySiteStateDTO mapStateToDTO(FactorySiteState state){
        return FactorySiteStateDTO
                .builder()
                .problems(state.getProblems().stream().map(this::stateProblemToDTOMapper).collect(Collectors.toList()))
                .warnings(state.getWarnings().stream().map(this::stateWarningToDTOMapper).collect(Collectors.toList()))
                .build();
    };


    default StateProblemDTO stateProblemToDTOMapper(StateProblem sp){
        switch (sp){
            case WorkShiftConsumablesLossProblem workShiftConsumablesLossProblem -> {
                return stateProblemToDTOConcreteMapper(workShiftConsumablesLossProblem);
            }
            case WorkShiftConsumablesTooMuchProblem workShiftConsumablesTooMuchProblem -> {
                return stateProblemToDTOConcreteMapper(workShiftConsumablesTooMuchProblem);
            }

            default -> throw new IllegalStateException("Unexpected value: " + sp);
        }

    }

    WorkShiftConsumablesTooMuchProblemDTO stateProblemToDTOConcreteMapper(WorkShiftConsumablesTooMuchProblem workShiftConsumablesTooMuchProblem);

    WorkShiftConsumablesLossProblemDTO stateProblemToDTOConcreteMapper(WorkShiftConsumablesLossProblem workShiftConsumablesLossProblem);


    default StateWarningDTO stateWarningToDTOMapper(StateWarning sp){
        switch (sp){
            case WorkShiftLossesWarning workShiftLossesWarning -> {
                return stateWarningToDTOConcreteMapper(workShiftLossesWarning);
            }
            case WorkShiftRemainsWarning workShiftRemainsWarning -> {
                return stateWarningToDTOConcreteMapper(workShiftRemainsWarning);
            }

            default -> throw new IllegalStateException("Unexpected value: " + sp);
        }

    }

    WorkShiftRemainsWarningDTO stateWarningToDTOConcreteMapper(WorkShiftRemainsWarning workShiftRemainsWarning);

    WorkShiftLossesWarningDTO stateWarningToDTOConcreteMapper(WorkShiftLossesWarning workShiftLossesWarning);



}

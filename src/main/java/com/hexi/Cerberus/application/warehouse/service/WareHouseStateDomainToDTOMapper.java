package com.hexi.Cerberus.application.warehouse.service;

import com.hexi.Cerberus.application.mapper.DTO.StateProblemDTO;
import com.hexi.Cerberus.application.mapper.DTO.StateWarningDTO;
import com.hexi.Cerberus.application.mapper.DTO.problems.InvalidStorageStateProblemDTO;
import com.hexi.Cerberus.application.mapper.DTO.problems.InventarisationReportProblemDTO;
import com.hexi.Cerberus.application.mapper.DTO.problems.ReleasedTooMuchReportProblemDTO;
import com.hexi.Cerberus.application.mapper.DTO.problems.WorkShiftReplenishedTooMuchReportProblemDTO;
import com.hexi.Cerberus.application.mapper.DTO.warnings.UnsatisfiedSupplyRequirementReportWarningDTO;
import com.hexi.Cerberus.application.mapper.DTO.warnings.UnsatisfiedWorkShiftReportWarningDTO;
import com.hexi.Cerberus.application.warehouse.service.DTO.WareHouseStateDTO;
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
public interface WareHouseStateDomainToDTOMapper {
    default WareHouseStateDTO mapStateToDTO(WareHouseState wareHouseState){
        return WareHouseStateDTO
                .builder()
                .problems(wareHouseState.getProblems().stream().map(this::stateProblemToDTOMapper).collect(Collectors.toList()))
                .warnings(wareHouseState.getWarnings().stream().map(this::stateWarningToDTOMapper).collect(Collectors.toList()))
                .items(wareHouseState.getItems().entrySet().stream().collect(Collectors.toMap(t -> t.getKey().getId(), t -> t.getValue())))
                .build();
    };



    default StateProblemDTO stateProblemToDTOMapper(StateProblem sp){
        switch (sp){
            case InvalidStorageStateProblem invalidStorageStateProblem -> {
                return stateProblemToDTOConcreteMapper(invalidStorageStateProblem);
            }
            case InventarisationReportProblem inventarisationReportProblem -> {
                return stateProblemToDTOConcreteMapper(inventarisationReportProblem);
            }
            case ReleasedTooMuchReportProblem releasedTooMuchReportProblem -> {
                return stateProblemToDTOConcreteMapper(releasedTooMuchReportProblem);
            }
            case WorkShiftReplenishedTooMuchReportProblem workShiftReplenishedTooMuchReportProblem -> {
                return stateProblemToDTOConcreteMapper(workShiftReplenishedTooMuchReportProblem);
            }

            default -> throw new IllegalStateException("Unexpected value: " + sp);
        }

    }


    InvalidStorageStateProblemDTO stateProblemToDTOConcreteMapper(InvalidStorageStateProblem invalidStorageStateProblem);

    InventarisationReportProblemDTO stateProblemToDTOConcreteMapper(InventarisationReportProblem inventarisationReportProblem);

    WorkShiftReplenishedTooMuchReportProblemDTO stateProblemToDTOConcreteMapper(WorkShiftReplenishedTooMuchReportProblem workShiftReplenishedTooMuchReportProblem);

    ReleasedTooMuchReportProblemDTO stateProblemToDTOConcreteMapper(ReleasedTooMuchReportProblem releasedTooMuchReportProblem);



    default StateWarningDTO stateWarningToDTOMapper(StateWarning sp){
        switch (sp){
            case UnsatisfiedSupplyRequirementReportWarning unsatisfiedSupplyRequirementReportWarning -> {
                return stateWarningToDTOConcreteMapper(unsatisfiedSupplyRequirementReportWarning);
            }
            case UnsatisfiedWorkShiftReportWarning unsatisfiedWorkShiftReportWarning -> {
                return stateWarningToDTOConcreteMapper(unsatisfiedWorkShiftReportWarning);
            }


            default -> throw new IllegalStateException("Unexpected value: " + sp);
        }

    }

    UnsatisfiedSupplyRequirementReportWarningDTO stateWarningToDTOConcreteMapper(UnsatisfiedSupplyRequirementReportWarning unsatisfiedSupplyRequirementReportWarning);

    UnsatisfiedWorkShiftReportWarningDTO stateWarningToDTOConcreteMapper(UnsatisfiedWorkShiftReportWarning unsatisfiedWorkShiftReportWarning);

}

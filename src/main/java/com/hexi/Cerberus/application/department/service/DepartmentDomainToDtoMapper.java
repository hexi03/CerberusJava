package com.hexi.Cerberus.application.department.service;

import com.hexi.Cerberus.application.department.service.DTO.DepartmentDetailsDTO;
import com.hexi.Cerberus.domain.department.Department;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepartmentDomainToDtoMapper {

    default DepartmentDetailsDTO departmentToDetailsDTO(Department department) {
        return DepartmentDetailsDTO
                .builder()
                .id(department.getId())
                .name(department.getName())
                .factorySites(
                        department
                                .getFactorySites()
                                .stream()
                                .map((factorySite -> factorySite.getId()))
                                .toList()
                )
                .wareHouses(
                        department
                                .getWareHouses()
                                .stream()
                                .map((wareHouse -> wareHouse.getId()))
                                .toList()
                )

                .build();
    }

}

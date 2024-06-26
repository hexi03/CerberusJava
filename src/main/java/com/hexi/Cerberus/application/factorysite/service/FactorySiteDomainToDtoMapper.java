package com.hexi.Cerberus.application.factorysite.service;

import com.hexi.Cerberus.application.factorysite.service.DTO.FactorySiteDetailsDTO;
import com.hexi.Cerberus.domain.factorysite.FactorySite;
import org.mapstruct.Mapper;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface FactorySiteDomainToDtoMapper {

    default FactorySiteDetailsDTO factorySiteToDetailsDTO(FactorySite factorySite) {
        return FactorySiteDetailsDTO
                .builder()
                .id(factorySite.getId())
                .departmentId(factorySite.getParentDepartment().getId())
                .name(factorySite.getName())
                .suppliers(
                        factorySite
                                .getSuppliers()
                                .stream()
                                .map(wareHouse -> wareHouse.getId())
                                .collect(Collectors.toList())
                )
                .build();
    }

}

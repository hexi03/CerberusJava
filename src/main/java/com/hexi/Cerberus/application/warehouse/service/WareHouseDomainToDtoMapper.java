package com.hexi.Cerberus.application.warehouse.service;

import com.hexi.Cerberus.application.warehouse.service.DTO.WareHouseDetailsDTO;
import com.hexi.Cerberus.domain.warehouse.WareHouse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WareHouseDomainToDtoMapper {

    default WareHouseDetailsDTO wareHouseToDetailsDTO(WareHouse wareHouse) {
        return WareHouseDetailsDTO
                .builder()
                .id(wareHouse.getId())
                .departmentId(wareHouse.getParentDepartment().getId())
                .name(wareHouse.getName())
                .build();
    }

}

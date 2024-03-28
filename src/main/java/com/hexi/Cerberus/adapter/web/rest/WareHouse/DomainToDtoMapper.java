package com.hexi.Cerberus.adapter.web.rest.WareHouse;

import com.hexi.Cerberus.adapter.web.rest.WareHouse.DTO.WareHouseDetailsDTO;
import com.hexi.Cerberus.domain.factorysite.FactorySite;
import com.hexi.Cerberus.domain.warehouse.WareHouse;
import org.mapstruct.Mapper;

@Mapper
public interface DomainToDtoMapper {

    default WareHouseDetailsDTO wareHouseToDetailsDTO(WareHouse wareHouse){
        return WareHouseDetailsDTO
                .builder()
                .id(wareHouse.getId())
                .departmentId(wareHouse.getParentDepartment().getId())
                .name(wareHouse.getName())
                .build();
    };
}

package com.hexi.Cerberus.domain.warehouse.command.WareHouse;

import com.hexi.Cerberus.domain.warehouse.WareHouse;
import com.hexi.Cerberus.domain.warehouse.command.WareHouse.DTO.WareHouseDetailsDTO;
import org.mapstruct.Mapper;

@Mapper
public interface DomainToDtoMapper {

    WareHouseDetailsDTO wareHouseToDetailsDTO(WareHouse wareHouse);
}

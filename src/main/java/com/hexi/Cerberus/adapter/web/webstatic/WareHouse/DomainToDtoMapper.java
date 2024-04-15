package com.hexi.Cerberus.adapter.web.webstatic.WareHouse;

import com.hexi.Cerberus.domain.warehouse.WareHouse;
import com.hexi.Cerberus.adapter.web.webstatic.WareHouse.DTO.WareHouseDetailsDTO;
import org.mapstruct.Mapper;

@Mapper
public interface DomainToDtoMapper {

    WareHouseDetailsDTO wareHouseToDetailsDTO(WareHouse wareHouse);
}

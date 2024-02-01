package com.hexi.Cerberus.application.impl;

import com.hexi.Cerberus.domain.commontypes.WareHouseID;
import com.hexi.Cerberus.application.DTO.WareHouseDTO;
import com.hexi.Cerberus.application.Mapper;
import com.hexi.Cerberus.application.WareHouseService;

import java.util.List;

public class WareHouseServiceImpl implements WareHouseService {

    private com.hexi.Cerberus.domain.services.WareHouseService wareHouseService;

    // Добавьте мапперы
    private Mapper mapper;

    public WareHouseServiceImpl(
            com.hexi.Cerberus.domain.services.WareHouseService wareHouseService,
            Mapper mapper
    ) {
        this.wareHouseService = wareHouseService;
        this.mapper = mapper;
    }
    @Override
    public WareHouseDTO getWareHouseById(WareHouseID id) {
        return mapper.mapWareHouseDTO(wareHouseService.getWareHouseById(id));
    }

    @Override
    public List<WareHouseDTO> getAllWareHouses() {
        return mapper.mapWareHouseDTO(wareHouseService.getWareHouses());
    }

    @Override
    public void createWareHouse(WareHouseDTO dto) {
        wareHouseService.createWareHouse(mapper.mapWareHouse(dto));
    }

    @Override
    public void updateWareHouse(WareHouseDTO dto) {
        wareHouseService.updateWareHouse(mapper.mapWareHouse(dto));
    }

    @Override
    public void deleteWareHouse(WareHouseID id) {
        wareHouseService.deleteWareHouse(id);
    }
}

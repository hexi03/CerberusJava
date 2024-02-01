package com.hexi.Cerberus.domain.services.impl;

import com.hexi.Cerberus.domain.commontypes.DepartmentID;
import com.hexi.Cerberus.domain.commontypes.WareHouseID;
import com.hexi.Cerberus.domain.exceptions.WareHouseNotFoundException;
import com.hexi.Cerberus.domain.entities.WareHouse;
import com.hexi.Cerberus.domain.repository.DepartmentRepository;
import com.hexi.Cerberus.domain.repository.WareHouseRepository;
import com.hexi.Cerberus.domain.services.WareHouseService;

import java.util.List;
import java.util.UUID;

public class WareHouseServiceImpl implements WareHouseService {

    private WareHouseRepository wareHouseRepository;

    private DepartmentRepository departmentRepository;
    public WareHouseServiceImpl(WareHouseRepository wareHouseRepository, DepartmentRepository departmentRepository) {
        this.wareHouseRepository = wareHouseRepository;
        this.departmentRepository = departmentRepository;
    }
    @Override
    public List<WareHouse> getWareHouses() {
        return wareHouseRepository.getAll();
    }

    @Override
    public WareHouse getWareHouseById(WareHouseID id) {
        if(wareHouseRepository.isExists(id.getId()))
            return wareHouseRepository.getById(id.getId());
        else
            throw new WareHouseNotFoundException();
    }

    @Override
    public List<WareHouse> getWareHouseByDepartmentId(DepartmentID id) {
        if(departmentRepository.isExists(id.getId()))
            return wareHouseRepository.getByDepartmentId(id.getId());
        else
            throw new WareHouseNotFoundException();

    }

    @Override
    public void createWareHouse(WareHouse wareHouse) {
        wareHouse.setId(new WareHouseID(UUID.randomUUID().toString()));
        wareHouseRepository.append(wareHouse);
    }

    @Override
    public void updateWareHouse(WareHouse wareHouse) {
        if(wareHouseRepository.isExists(wareHouse.getId().getId())) {
            wareHouse.setDepartmentId(wareHouseRepository.getById(wareHouse.getId().getId()).getDepartmentId());
            wareHouseRepository.update(wareHouse);
        }else
            throw new WareHouseNotFoundException();


    }

    @Override
    public void deleteWareHouse(WareHouseID id) {
        if(wareHouseRepository.isExists(id.getId()))
            wareHouseRepository.deleteById(id.getId());
        else
            throw new WareHouseNotFoundException();

    }
}

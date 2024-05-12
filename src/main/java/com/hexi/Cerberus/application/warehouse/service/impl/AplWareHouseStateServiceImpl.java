package com.hexi.Cerberus.application.warehouse.service.impl;

import com.hexi.Cerberus.application.warehouse.service.AplWareHouseStateService;
import com.hexi.Cerberus.application.warehouse.service.DTO.WareHouseStateDTO;
import com.hexi.Cerberus.application.warehouse.service.WareHouseStateDomainToDTOMapper;
import com.hexi.Cerberus.domain.service.WareHouseStateService;
import com.hexi.Cerberus.domain.warehouse.WareHouse;
import com.hexi.Cerberus.domain.warehouse.WareHouseID;
import com.hexi.Cerberus.domain.warehouse.repository.WareHouseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AplWareHouseStateServiceImpl implements AplWareHouseStateService {
    private final WareHouseStateService wareHouseStateService;
    private final WareHouseRepository wareHouseRepository;
    private final WareHouseStateDomainToDTOMapper wareHouseStateDomainToDTOMapper;

    @Override
    public WareHouseStateDTO getWareHouseState(WareHouseID id) {
        Optional<WareHouse> wh = wareHouseRepository.findById(id);
        if (wh.isEmpty()) throw new RuntimeException(String.format("There are no such warehouse with id [%s]", id.toString()));

        return wareHouseStateDomainToDTOMapper.mapStateToDTO(wareHouseStateService.getWareHouseState(wh.get()));
    }
}

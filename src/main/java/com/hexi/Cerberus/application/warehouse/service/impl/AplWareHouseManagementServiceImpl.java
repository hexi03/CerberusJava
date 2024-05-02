package com.hexi.Cerberus.application.warehouse.service.impl;

import com.hexi.Cerberus.application.user.service.DTO.UserDetailsDTO;
import com.hexi.Cerberus.application.warehouse.service.DTO.WareHouseDetailsDTO;
import com.hexi.Cerberus.application.warehouse.service.WareHouseDomainToDtoMapper;
import com.hexi.Cerberus.application.warehouse.service.WareHouseManagementService;
import com.hexi.Cerberus.domain.user.User;
import com.hexi.Cerberus.domain.user.UserID;
import com.hexi.Cerberus.domain.warehouse.WareHouse;
import com.hexi.Cerberus.domain.warehouse.WareHouseFactory;
import com.hexi.Cerberus.domain.warehouse.WareHouseID;
import com.hexi.Cerberus.domain.warehouse.WareHouseUpdater;
import com.hexi.Cerberus.domain.warehouse.command.CreateWareHouseCmd;
import com.hexi.Cerberus.domain.warehouse.command.UpdateWareHouseDetailsCmd;
import com.hexi.Cerberus.domain.warehouse.repository.WareHouseRepository;
import com.hexi.Cerberus.infrastructure.messaging.MessagePublisher;
import com.hexi.Cerberus.infrastructure.query.Query;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class AplWareHouseManagementServiceImpl implements WareHouseManagementService {
    public final WareHouseRepository wareHouseRepository;
    public final MessagePublisher messagePublisher;
    public final MutableAclService aclService;
    public final WareHouseFactory wareHouseFactory;
    public final WareHouseUpdater wareHouseUpdater;
    public final WareHouseDomainToDtoMapper wareHouseDomainToDtoMapper;

    @Override
    public Optional<WareHouseDetailsDTO> displayBy(WareHouseID id) {
        Optional<WareHouse> wareHouse = wareHouseRepository.findById(id);
        return wareHouse.map(wareHouseDomainToDtoMapper::wareHouseToDetailsDTO);
    }

    @Override
    public List<WareHouseDetailsDTO> displayAllBy(Query query) {
        return ((List<WareHouse>)wareHouseRepository.findAllWithQuery(query)).stream()
                .map(wareHouseDomainToDtoMapper::wareHouseToDetailsDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<WareHouseDetailsDTO> displayAll() {
        return ((List<WareHouse>)wareHouseRepository.findAll()).stream()
                .map(wareHouseDomainToDtoMapper::wareHouseToDetailsDTO)
                .collect(Collectors.toList());

    }

    @Override
    public WareHouseDetailsDTO create(CreateWareHouseCmd cmd) {
        cmd.validate().onFailedThrow();
        WareHouse wareHouse = wareHouseFactory.from(cmd);
        wareHouseRepository.append(wareHouse);
        wareHouse.initAcl(aclService);
        messagePublisher.publish(wareHouse.edjectEvents());
        return wareHouseDomainToDtoMapper.wareHouseToDetailsDTO(wareHouse);

    }

    @Override
    public void updateDetails(UpdateWareHouseDetailsCmd cmd) {
        cmd.validate().onFailedThrow();
        Optional<WareHouse> wareHouse = wareHouseRepository.findById(cmd.getWareHouseId());
        wareHouse.orElseThrow(() -> new RuntimeException(String.format("There are no warehouse with id %s", cmd.getWareHouseId().toString())));
        wareHouseUpdater.updateBy(wareHouse.get(), cmd);
        wareHouseRepository.update(wareHouse.get());
        messagePublisher.publish(wareHouse.get().edjectEvents());


    }

    @Override
    public void delete(WareHouseID id) {
        wareHouseRepository.deleteById(id);
    }
}

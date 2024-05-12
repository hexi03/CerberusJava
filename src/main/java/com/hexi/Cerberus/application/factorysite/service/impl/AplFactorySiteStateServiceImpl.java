package com.hexi.Cerberus.application.factorysite.service.impl;



import com.hexi.Cerberus.application.factorysite.service.AplFactorySiteStateService;
import com.hexi.Cerberus.application.factorysite.service.DTO.FactorySiteStateDTO;
import com.hexi.Cerberus.application.factorysite.service.FactorySiteStateDomainToDTOMapper;
import com.hexi.Cerberus.domain.factorysite.FactorySite;
import com.hexi.Cerberus.domain.factorysite.FactorySiteID;
import com.hexi.Cerberus.domain.factorysite.repository.FactorySiteRepository;
import com.hexi.Cerberus.domain.service.FactorySiteStateService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AplFactorySiteStateServiceImpl implements AplFactorySiteStateService {
    private final FactorySiteStateService factorySiteStateService;
    private final FactorySiteRepository factorySiteRepository;
    private final FactorySiteStateDomainToDTOMapper factorySiteStateDomainToDTOMapper;

    @Override
    public FactorySiteStateDTO getFactorySiteState(FactorySiteID id) {
        Optional<FactorySite> wh = factorySiteRepository.findById(id);
        if (wh.isEmpty()) throw new RuntimeException(String.format("There are no such FactorySite with id [%s]", id.toString()));

        return factorySiteStateDomainToDTOMapper.mapStateToDTO(factorySiteStateService.getFactorySiteState(wh.get()));
    }
}
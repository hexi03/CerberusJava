package com.hexi.Cerberus.application.impl;

import com.hexi.Cerberus.domain.commontypes.FactorySiteID;
import com.hexi.Cerberus.application.FactorySiteService;
import com.hexi.Cerberus.application.DTO.FactorySiteDTO;
import com.hexi.Cerberus.application.Mapper;

import java.util.List;

public class FactorySiteServiceImpl implements FactorySiteService {

    private com.hexi.Cerberus.domain.services.FactorySiteService factorySiteService;

    // Добавьте мапперы
    private Mapper mapper;

    public FactorySiteServiceImpl(
            com.hexi.Cerberus.domain.services.FactorySiteService factorySiteService,
            Mapper mapper
    ) {
        this.factorySiteService = factorySiteService;
        this.mapper = mapper;
    }
    @Override
    public FactorySiteDTO getFactorySiteById(FactorySiteID id) {
        return mapper.mapFactorySiteDTO(factorySiteService.getFactorySiteById(id));
    }

    @Override
    public List<FactorySiteDTO> getAllFactorySites() {
        return mapper.mapFactorySiteDTO(factorySiteService.getFactorySites());
    }

    @Override
    public void createFactorySite(FactorySiteDTO dto) {
        factorySiteService.createFactorySite(mapper.mapFactorySite(dto));
    }

    @Override
    public void updateFactorySite(FactorySiteDTO dto) {
        factorySiteService.updateFactorySite(mapper.mapFactorySite(dto));
    }

    @Override
    public void deleteFactorySite(FactorySiteID id) {
        factorySiteService.deleteFactorySite(id);
    }
}

package com.hexi.Cerberus.application;

import com.hexi.Cerberus.domain.commontypes.FactorySiteID;
import com.hexi.Cerberus.application.DTO.FactorySiteDTO;

import java.util.List;

public interface FactorySiteService {
    FactorySiteDTO getFactorySiteById(FactorySiteID id);
    List<FactorySiteDTO> getAllFactorySites();
    void createFactorySite(FactorySiteDTO dto);
    void updateFactorySite(FactorySiteDTO dto);
    void deleteFactorySite(FactorySiteID id);

    void updateFactorySiteSupply(FactorySiteSupplyDTO dto);
}

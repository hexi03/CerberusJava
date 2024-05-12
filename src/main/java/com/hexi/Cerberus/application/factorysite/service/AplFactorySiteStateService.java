package com.hexi.Cerberus.application.factorysite.service;

import com.hexi.Cerberus.application.factorysite.service.DTO.FactorySiteStateDTO;
import com.hexi.Cerberus.domain.factorysite.FactorySiteID;

public interface AplFactorySiteStateService {
    FactorySiteStateDTO getFactorySiteState(FactorySiteID id);

}

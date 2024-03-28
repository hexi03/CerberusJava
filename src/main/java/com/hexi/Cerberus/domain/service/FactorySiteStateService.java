package com.hexi.Cerberus.domain.service;

import com.hexi.Cerberus.domain.factorysite.FactorySite;
import com.hexi.Cerberus.domain.factorysite.FactorySiteState;

public interface FactorySiteStateService {

    FactorySiteState getFactorySiteState(FactorySite factorySite);
}

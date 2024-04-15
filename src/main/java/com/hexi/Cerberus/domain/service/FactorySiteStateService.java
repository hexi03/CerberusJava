package com.hexi.Cerberus.domain.service;

import com.hexi.Cerberus.domain.factorysite.FactorySite;
import com.hexi.Cerberus.domain.factorysite.FactorySiteState;
import org.springframework.stereotype.Service;

@Service
public interface FactorySiteStateService {

    FactorySiteState getFactorySiteState(FactorySite factorySite);
}

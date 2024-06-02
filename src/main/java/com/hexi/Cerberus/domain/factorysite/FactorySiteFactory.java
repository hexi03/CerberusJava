package com.hexi.Cerberus.domain.factorysite;

import com.hexi.Cerberus.application.factorysite.service.command.CreateFactorySiteCmd;
import org.springframework.stereotype.Component;

@Component
public interface FactorySiteFactory {
    FactorySite from(CreateFactorySiteCmd cmd);
}

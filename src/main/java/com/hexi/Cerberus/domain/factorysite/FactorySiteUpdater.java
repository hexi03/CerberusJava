package com.hexi.Cerberus.domain.factorysite;

import com.hexi.Cerberus.domain.factorysite.command.UpdateFactorySiteDetailsCmd;
import org.springframework.stereotype.Component;

@Component
public class FactorySiteUpdater {
    public void updateBy(FactorySite factorySite, UpdateFactorySiteDetailsCmd cmd) {
        factorySite.setName(cmd.getName());
    }
}
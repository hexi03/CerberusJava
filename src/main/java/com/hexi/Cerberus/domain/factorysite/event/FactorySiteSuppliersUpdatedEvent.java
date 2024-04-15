package com.hexi.Cerberus.domain.factorysite.event;

import com.hexi.Cerberus.domain.factorysite.FactorySiteID;
import com.hexi.Cerberus.domain.warehouse.WareHouseID;
import com.hexi.Cerberus.infrastructure.event.EventId;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class FactorySiteSuppliersUpdatedEvent extends FactorySiteStorageUpdatedEvent {
    FactorySiteID factorySiteId;
    EventId id;

    public FactorySiteSuppliersUpdatedEvent(FactorySiteID facId) {
        factorySiteId = facId;
        id = EventId.generate();
    }

    @Override
    public EventId getId() {
        return id;
    }
}

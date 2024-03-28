package com.hexi.Cerberus.domain.department.event;

import com.hexi.Cerberus.domain.department.DepartmentID;
import com.hexi.Cerberus.domain.factorysite.FactorySiteID;
import com.hexi.Cerberus.domain.warehouse.WareHouse;
import com.hexi.Cerberus.domain.warehouse.WareHouseID;
import com.hexi.Cerberus.infrastructure.event.DomainEvent;
import com.hexi.Cerberus.infrastructure.event.EventId;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WareHouseRegisteredEvent implements DomainEvent {
    EventId eventId;
    DepartmentID departmentId;
    WareHouseID wareHouseId;
    public WareHouseRegisteredEvent(DepartmentID depId, WareHouseID whId){
        departmentId = depId;
        wareHouseId = whId;
        eventId = EventId.generate();
    }
    @Override
    public EventId getId() {
        return null;
    }
}

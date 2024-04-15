package com.hexi.Cerberus.domain.department.event;

import com.hexi.Cerberus.domain.department.DepartmentID;
import com.hexi.Cerberus.domain.factorysite.FactorySiteID;
import com.hexi.Cerberus.infrastructure.event.DomainEvent;
import com.hexi.Cerberus.infrastructure.event.EventId;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FactorySiteRegisteredEvent implements DomainEvent {
    EventId eventId;
    DepartmentID departmentId;
    FactorySiteID factorySiteId;

    public FactorySiteRegisteredEvent(DepartmentID depId, FactorySiteID facId) {
        departmentId = depId;
        factorySiteId = facId;
        eventId = EventId.generate();
    }

    @Override
    public EventId getId() {
        return eventId;
    }
}

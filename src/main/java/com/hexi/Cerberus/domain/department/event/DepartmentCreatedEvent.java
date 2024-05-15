package com.hexi.Cerberus.domain.department.event;

import com.hexi.Cerberus.domain.department.DepartmentID;
import com.hexi.Cerberus.infrastructure.event.EventId;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DepartmentCreatedEvent extends DepartmentStorageUpdatedEvent {
    EventId id;
    DepartmentID departmentId;

    public DepartmentCreatedEvent(
            DepartmentID departmentId
    ) {
        this.departmentId = departmentId;
        id = EventId.generate();
    }

    @Override
    public EventId getId() {
        return id;
    }
}

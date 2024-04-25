package com.hexi.Cerberus.domain.department;

import com.hexi.Cerberus.infrastructure.entity.EntityId;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class DepartmentID implements EntityId<UUID> {
    public final UUID id;

    public DepartmentID() {
        id = UUID.randomUUID();
    }

    public DepartmentID(DepartmentID departmentID) {
        this.id = departmentID.getId();
    }
}

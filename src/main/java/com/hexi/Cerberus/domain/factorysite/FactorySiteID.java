package com.hexi.Cerberus.domain.factorysite;

import com.hexi.Cerberus.domain.department.DepartmentID;
import com.hexi.Cerberus.infrastructure.entity.EntityId;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class FactorySiteID implements EntityId<UUID> {
    public final UUID id;

    public FactorySiteID() {
        id = UUID.randomUUID();
    }

    public FactorySiteID(FactorySiteID factorySiteID) {
        id = factorySiteID.getId();
    }
}

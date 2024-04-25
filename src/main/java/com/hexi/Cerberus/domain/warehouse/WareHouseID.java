package com.hexi.Cerberus.domain.warehouse;

import com.hexi.Cerberus.domain.department.DepartmentID;
import com.hexi.Cerberus.infrastructure.entity.EntityId;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class WareHouseID implements EntityId<UUID> {
    public final UUID id;

    public WareHouseID() {
        id = UUID.randomUUID();
    }

    public WareHouseID(WareHouseID wareHouseID) {
        id = wareHouseID.getId();
    }
}

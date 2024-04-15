package com.hexi.Cerberus.domain.item;

import com.hexi.Cerberus.infrastructure.entity.EntityId;
import lombok.Data;

import java.util.UUID;

@Data
public class ItemID implements EntityId<UUID> {
    public final UUID id;

    public ItemID() {
        id = UUID.randomUUID();
    }

    public ItemID(UUID id) {
        this.id = id;
    }
}

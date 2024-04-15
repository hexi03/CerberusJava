package com.hexi.Cerberus.domain.product;

import com.hexi.Cerberus.infrastructure.entity.EntityId;
import lombok.Data;

import java.util.UUID;

@Data
public class ProductID implements EntityId<UUID> {
    public final UUID id;

    public ProductID() {
        id = UUID.randomUUID();
    }

    public ProductID(UUID id) {
        this.id = id;
    }
}

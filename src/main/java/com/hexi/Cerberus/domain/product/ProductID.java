package com.hexi.Cerberus.domain.product;

import com.hexi.Cerberus.infrastructure.entity.EntityID;
import com.hexi.Cerberus.infrastructure.entity.UUIDBasedEntityID;
import lombok.Getter;

import java.util.UUID;

@Getter
public class ProductID implements EntityID<UUID> {
    public final UUID id;

    public ProductID(UUIDBasedEntityID id) {
        this.id = id.getId();
    }
    public ProductID(ProductID id) {
        this.id = id.getId();
    }


    public ProductID() {
        id = UUID.randomUUID();
    }

    public ProductID(UUID id) {
        this.id = id;
    }

    public ProductID(String id) { this.id = UUID.fromString(id); }
    public String toString() {
        return this.getId().toString();
    }


    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ProductID)) return false;
        final ProductID other = (ProductID) o;
        if (!other.canEqual((ProductID) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ProductID;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        return result;
    }
}

package com.hexi.Cerberus.domain.factorysite;

import com.hexi.Cerberus.domain.department.DepartmentID;
import com.hexi.Cerberus.infrastructure.entity.EntityID;
import com.hexi.Cerberus.infrastructure.entity.UUIDBasedEntityID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.UUID;

@Data

@Getter
public class FactorySiteID implements EntityID<UUID> {
    public final UUID id;

    public FactorySiteID(UUIDBasedEntityID id) {
        this.id = id.getId();
    }
    public FactorySiteID(FactorySiteID id) {
        this.id = id.getId();
    }

    public FactorySiteID() {
        id = UUID.randomUUID();
    }

    public FactorySiteID(UUID id) {
        this.id = id;
    }

    public FactorySiteID(String id) { this.id = UUID.fromString(id); }
    public String toString() {
        return this.getId().toString();
    }


    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof FactorySiteID)) return false;
        final FactorySiteID other = (FactorySiteID) o;
        if (!other.canEqual((FactorySiteID) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof FactorySiteID;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        return result;
    }
}

package com.hexi.Cerberus.domain.item;

import com.hexi.Cerberus.domain.department.DepartmentID;
import com.hexi.Cerberus.domain.group.GroupID;
import com.hexi.Cerberus.infrastructure.entity.EntityID;
import com.hexi.Cerberus.infrastructure.entity.UUIDBasedEntityID;
import lombok.Getter;

import java.util.UUID;

@Getter
public class ItemID implements EntityID<UUID> {
    public final UUID id;

    public ItemID(UUIDBasedEntityID id) {
        this.id = id.getId();
    }

    public ItemID(ItemID id) {
        this.id = id.getId();
    }
    public ItemID() {
        id = UUID.randomUUID();
    }

    public ItemID(UUID id) {
        this.id = id;
    }

    public ItemID(String id) { this.id = UUID.fromString(id); }
    public String toString() {
        return this.getId().toString();
    }


    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ItemID)) return false;
        final ItemID other = (ItemID) o;
        if (!other.canEqual((ItemID) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ItemID;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        return result;
    }
}
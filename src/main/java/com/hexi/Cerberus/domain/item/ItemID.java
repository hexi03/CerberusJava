package com.hexi.Cerberus.domain.item;

import com.hexi.Cerberus.infrastructure.entity.EntityId;

import java.util.UUID;

public class ItemID implements EntityId<UUID> {
    public final UUID id;

    public ItemID() {
        id = UUID.randomUUID();
    }

    public ItemID(UUID id) {
        this.id = id;
    }

    public ItemID(ItemID itemId) {
        this.id = itemId.getId();
    }

    public ItemID(String id) {
        this.id = UUID.fromString(id);
    }

    public UUID getId() {
        return this.id;
    }

    public String toString() {
        return this.getId().toString();
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ItemID)) return false;
        final ItemID other = (ItemID) o;
        if (!other.canEqual((Object) this)) return false;
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

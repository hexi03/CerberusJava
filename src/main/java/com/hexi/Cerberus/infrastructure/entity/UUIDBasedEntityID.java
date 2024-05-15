package com.hexi.Cerberus.infrastructure.entity;

import com.hexi.Cerberus.domain.department.DepartmentID;
import lombok.Getter;
import org.springframework.core.convert.converter.Converter;

import java.util.UUID;
@Getter
public class UUIDBasedEntityID implements EntityID<UUID> {
    public final UUID id;

    public UUIDBasedEntityID() {
        id = UUID.randomUUID();
    }

    public UUIDBasedEntityID(UUID id) {
        this.id = id;
    }

    public UUIDBasedEntityID(String id) { this.id = UUID.fromString(id); }
    public String toString() {
        return this.getId().toString();
    }


    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof UUIDBasedEntityID)) return false;
        final UUIDBasedEntityID other = (UUIDBasedEntityID) o;
        if (!other.canEqual((UUIDBasedEntityID) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof UUIDBasedEntityID;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        return result;
    }



}

package com.hexi.Cerberus.domain.report;

import com.hexi.Cerberus.infrastructure.entity.EntityID;
import com.hexi.Cerberus.infrastructure.entity.UUIDBasedEntityID;
import lombok.Getter;

import java.util.UUID;

@Getter
public class ReportID implements EntityID<UUID> {
    public final UUID id;

    public ReportID(UUIDBasedEntityID id) {
        this.id = id.getId();
    }
    public ReportID(ReportID id) {
        this.id = id.getId();
    }

    public ReportID() {
        id = UUID.randomUUID();
    }

    public ReportID(UUID id) {
        this.id = id;
    }

    public ReportID(String id) { this.id = UUID.fromString(id); }
    public String toString() {
        return this.getId().toString();
    }


    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ReportID)) return false;
        final ReportID other = (ReportID) o;
        if (!other.canEqual((ReportID) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ReportID;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        return result;
    }
}
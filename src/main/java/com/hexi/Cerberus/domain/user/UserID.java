package com.hexi.Cerberus.domain.user;

import com.hexi.Cerberus.domain.department.DepartmentID;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.infrastructure.entity.EntityID;
import com.hexi.Cerberus.infrastructure.entity.UUIDBasedEntityID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UserID implements EntityID<UUID> {
    public final UUID id;

    public UserID(UUIDBasedEntityID id) {
        this.id = id.getId();
    }

    public UserID(UserID id) {
        this.id = id.getId();
    }

    public UserID() {
        id = UUID.randomUUID();
    }

    public UserID(UUID id) {
        this.id = id;
    }

    public UserID(String id) { this.id = UUID.fromString(id); }
    public String toString() {
        return this.getId().toString();
    }


    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof UserID)) return false;
        final UserID other = (UserID) o;
        if (!other.canEqual((UserID) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof UserID;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        return result;
    }
}

package com.hexi.Cerberus.domain.group;

import com.hexi.Cerberus.infrastructure.entity.EntityId;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class GroupID implements EntityId<UUID> {
    public final UUID id;

    public GroupID(String id) {
        this.id = UUID.fromString(id);
    }

    public GroupID() {
        id = UUID.randomUUID();
    }

    public GroupID(GroupID groupID) {
        id = groupID.getId();
    }
    public String toString() {
        return this.getId().toString();
    }
}

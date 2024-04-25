package com.hexi.Cerberus.domain.user;

import com.hexi.Cerberus.infrastructure.entity.EntityId;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class UserID implements EntityId<UUID> {
    public final UUID id;

    public UserID() {
        id = UUID.randomUUID();
    }

    public UserID(UserID userID) {
        id = userID.getId();
    }
}

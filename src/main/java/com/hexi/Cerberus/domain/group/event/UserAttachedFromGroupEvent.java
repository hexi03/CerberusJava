package com.hexi.Cerberus.domain.group.event;

import com.hexi.Cerberus.domain.group.GroupID;
import com.hexi.Cerberus.domain.user.UserID;
import com.hexi.Cerberus.infrastructure.event.DomainEvent;
import com.hexi.Cerberus.infrastructure.event.EventId;

public class UserAttachedFromGroupEvent implements DomainEvent {
    public final EventId id;
    GroupID groupId;
    UserID userId;

    public UserAttachedFromGroupEvent(GroupID groupId, UserID userId) {
        this.userId = userId;
        this.groupId = groupId;
        id = EventId.generate();
    }

    @Override
    public EventId getId() {
        return id;
    }
}

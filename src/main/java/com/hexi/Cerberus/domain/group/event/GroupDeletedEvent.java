package com.hexi.Cerberus.domain.group.event;

import com.hexi.Cerberus.domain.group.GroupID;
import com.hexi.Cerberus.domain.user.UserID;
import com.hexi.Cerberus.infrastructure.event.DomainEvent;
import com.hexi.Cerberus.infrastructure.event.EventId;


public class GroupDeletedEvent implements DomainEvent {
    public final EventId id;
    GroupID groupId;
    public GroupDeletedEvent(GroupID groupId) {
        this.groupId = groupId;
        id = EventId.generate();
    }

    @Override
    public EventId getId() {
        return id;
    }
}

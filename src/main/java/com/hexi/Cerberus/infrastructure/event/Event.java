package com.hexi.Cerberus.infrastructure.event;

import com.hexi.Cerberus.infrastructure.messaging.Message;

public interface Event extends Message {
    EventId getId();
}

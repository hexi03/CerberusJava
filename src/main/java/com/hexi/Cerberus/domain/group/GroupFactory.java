package com.hexi.Cerberus.domain.group;

import com.hexi.Cerberus.application.group.service.command.CreateGroupCmd;
import org.springframework.stereotype.Component;

@Component
public interface GroupFactory {
    Group from(CreateGroupCmd cmd);
}

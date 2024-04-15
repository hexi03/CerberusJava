package com.hexi.Cerberus.domain.group;

import com.hexi.Cerberus.domain.group.command.UpdateGroupDetailsCmd;
import org.springframework.stereotype.Component;

@Component
public class GroupUpdater {
    public void updateBy(Group group, UpdateGroupDetailsCmd cmd) {
        group.setName(cmd.getName());
    }
}

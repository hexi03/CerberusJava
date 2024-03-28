package com.hexi.Cerberus.domain.group;

import com.hexi.Cerberus.domain.group.command.CreateGroupCmd;

public class GroupFactory {
    public static Group from(CreateGroupCmd cmd) {
        return new Group(
                new GroupID(),
                cmd.getName()
        );
    }
}

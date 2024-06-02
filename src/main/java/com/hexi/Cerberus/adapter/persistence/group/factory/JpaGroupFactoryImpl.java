package com.hexi.Cerberus.adapter.persistence.group.factory;

import com.hexi.Cerberus.adapter.persistence.group.base.GroupModel;
import com.hexi.Cerberus.domain.group.Group;
import com.hexi.Cerberus.domain.group.GroupFactory;
import com.hexi.Cerberus.domain.group.GroupID;
import com.hexi.Cerberus.application.group.service.command.CreateGroupCmd;

public class JpaGroupFactoryImpl implements GroupFactory {
    public Group from(CreateGroupCmd cmd) {
        return new GroupModel(
                new GroupID(),
                cmd.getName()
        );
    }
}

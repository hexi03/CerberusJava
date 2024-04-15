package com.hexi.Cerberus.domain.group.repository;

import com.hexi.Cerberus.domain.group.Group;
import com.hexi.Cerberus.domain.group.GroupID;
import com.hexi.Cerberus.infrastructure.repository.Repository;

@org.springframework.stereotype.Repository
public interface GroupRepository<T extends Group, ID extends GroupID> extends Repository<T, ID> {

}

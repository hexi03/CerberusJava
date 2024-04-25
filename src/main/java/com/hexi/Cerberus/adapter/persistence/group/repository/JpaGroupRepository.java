package com.hexi.Cerberus.adapter.persistence.group.repository;

import com.hexi.Cerberus.adapter.persistence.group.base.GroupModel;
import com.hexi.Cerberus.domain.group.Group;
import com.hexi.Cerberus.domain.group.GroupID;
import com.hexi.Cerberus.domain.group.repository.GroupRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaGroupRepository<T extends GroupModel,ID extends GroupID> extends GroupRepository<T,ID>, JpaRepository<T,ID> {
}

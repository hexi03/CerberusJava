package com.hexi.Cerberus.domain.repository;

import com.hexi.Cerberus.domain.entities.GroupUserRelation;
import com.hexi.Cerberus.infrastructure.repository.Repository;

import java.util.List;

public interface GroupUserRelationsRepository extends Repository<GroupUserRelation> {
    List<GroupUserRelation> append(List<GroupUserRelation> user);
}

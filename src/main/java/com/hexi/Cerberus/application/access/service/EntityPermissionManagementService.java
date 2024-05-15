package com.hexi.Cerberus.application.access.service;

import com.hexi.Cerberus.domain.access.AccessUnit;
import com.hexi.Cerberus.domain.group.GroupID;
import com.hexi.Cerberus.infrastructure.entity.EntityID;
import org.springframework.security.acls.model.Permission;

import java.util.List;

public interface EntityPermissionManagementService {
    List<Permission> displayPermissions(EntityID resourceId, GroupID groupId) throws Exception;

    List<AccessUnit> getAccessInfo(EntityID resourceId) throws Exception;

    void setPermissions(AccessUnit accessUnit) throws Exception;
}

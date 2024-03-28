package com.hexi.Cerberus.application.access.service;

import com.hexi.Cerberus.domain.access.AccessUnit;
import com.hexi.Cerberus.domain.group.GroupID;
import com.hexi.Cerberus.infrastructure.entity.EntityId;
import org.springframework.security.acls.model.Permission;


import java.util.List;

public interface EntityPermissionManagementService {
    List<Permission> displayPermissions(EntityId resourceId, GroupID groupId) throws Exception;
    List<AccessUnit> getAccessInfo(EntityId resourceId) throws Exception;
    void setPermissions(AccessUnit accessUnit) throws Exception;
}

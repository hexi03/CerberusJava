package com.hexi.Cerberus.application.access.service;

import com.hexi.Cerberus.domain.access.AccessUnit;
import com.hexi.Cerberus.domain.group.GroupID;
import com.hexi.Cerberus.infrastructure.entity.EntityID;
import com.hexi.Cerberus.infrastructure.entity.UUIDBasedEntityID;
import org.springframework.security.acls.model.Permission;

import java.util.List;

public interface EntityPermissionManagementService {
    List<Permission> displayPermissions(UUIDBasedEntityID resourceId, UUIDBasedEntityID groupId) throws Exception;

    List<AccessUnit> getAccessInfo(UUIDBasedEntityID resourceId) throws Exception;

    void setPermissions(AccessUnit accessUnit) throws Exception;
}

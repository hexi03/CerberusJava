package com.hexi.Cerberus.application.access.service.impl;

import com.hexi.Cerberus.application.access.service.EntityPermissionManagementService;
import com.hexi.Cerberus.domain.access.AccessUnit;
import com.hexi.Cerberus.domain.access.BehavioredPermissionFactory;
import com.hexi.Cerberus.domain.access.PermissionRegistry;
import com.hexi.Cerberus.domain.department.Department;
import com.hexi.Cerberus.domain.department.DepartmentID;
import com.hexi.Cerberus.domain.department.repository.DepartmentRepository;
import com.hexi.Cerberus.domain.factorysite.FactorySite;
import com.hexi.Cerberus.domain.factorysite.FactorySiteID;
import com.hexi.Cerberus.domain.factorysite.repository.FactorySiteRepository;
import com.hexi.Cerberus.domain.group.GroupID;
import com.hexi.Cerberus.domain.warehouse.WareHouse;
import com.hexi.Cerberus.domain.warehouse.WareHouseID;
import com.hexi.Cerberus.domain.warehouse.repository.WareHouseRepository;
import com.hexi.Cerberus.infrastructure.entity.EntityID;
import com.hexi.Cerberus.infrastructure.entity.SecuredEntity;
import com.hexi.Cerberus.infrastructure.entity.UUIDBasedEntityID;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class AplEntityPermissionManagementServiceImpl implements EntityPermissionManagementService {
    private final DepartmentRepository departmentRepository;
    private final FactorySiteRepository factorySiteRepository;
    private final WareHouseRepository wareHouseRepository;
    private final PermissionRegistry permissionRegistry = PermissionRegistry.getInstance();
    private final MutableAclService aclService;

    @SneakyThrows
    @Override
    public List<Permission> displayPermissions(UUIDBasedEntityID resourceId, UUIDBasedEntityID groupId) {
        return getEntity(resourceId)
                .map(entity -> entity.getPermissions(aclService, getSid(groupId)))
                .orElseThrow(() -> new Exception(String.format("There is no entity with ID(%s)", resourceId.getId())));
    }

    @Override
    public List<AccessUnit> getAccessInfo(UUIDBasedEntityID resourceId) throws Exception {
        return getEntity(resourceId)
                .map(this::fetchAccessUnits)
                .orElseThrow(() -> new Exception(String.format("There is no entity with ID(%s)", resourceId.getId())));
    }

    @Override
    public void setPermissions(AccessUnit accessUnit) throws Exception {
        System.out.println("setPermissions AccessUnit: " + accessUnit);
        List<Permission> permissions = accessUnit.getPermissions().stream()
                .map(this::mapPermission)
                .collect(Collectors.toList());

        getEntity(accessUnit.getResourceId())
                .ifPresentOrElse(
                        entity -> entity.updatePermissions(aclService, getSid(accessUnit.getAccessorId()), permissions),
                        () -> {
                                throw new RuntimeException(String.format("There is no entity with ID(%s)", accessUnit.getResourceId().getId()));

                        }
                );
    }

    private Sid getSid(UUIDBasedEntityID accessorId) {
        return new PrincipalSid(accessorId.getId().toString());
    }

    private List<AccessUnit> fetchAccessUnits(SecuredEntity entity) {
        return entity.getPermissions(aclService).entrySet().stream()
                .map(sidListEntry -> AccessUnit.builder()
                        .resourceId(entity.getId())
                        .accessorId(new UUIDBasedEntityID(sidListEntry.getKey().toString()))
                        .permissions(sidListEntry.getValue().stream()
                                .map(permission -> permission instanceof BehavioredPermissionFactory.BehavioredPermission
                                        ? permissionRegistry.getPermissionNamePure((BehavioredPermissionFactory.BehavioredPermission) permission)
                                        : permissionRegistry.getPermissionName(permission))
                                .filter(Optional::isPresent)
                                .map(Optional::get)
                                .collect(Collectors.toList()))
                        .build())
                .toList();
    }

    private Optional<SecuredEntity> getEntity(EntityID resourceId) {
        if (resourceId instanceof DepartmentID) {
            return departmentRepository.findById((DepartmentID) resourceId).map(e -> (SecuredEntity) e);
        } else if (resourceId instanceof FactorySiteID) {
            return factorySiteRepository.findById((FactorySiteID) resourceId).map(e -> (SecuredEntity) e);
        } else if (resourceId instanceof WareHouseID) {
            return wareHouseRepository.findById((WareHouseID) resourceId).map(e -> (SecuredEntity) e);
        } else {
            return Optional.empty();
        }
    }

    private Permission mapPermission(String permission) {
        return switch (permission) {
            case "READ" -> BehavioredPermissionFactory.READ;
            case "MODIFY" -> BehavioredPermissionFactory.MODIFY;
            default -> throw new RuntimeException("Invalid permission: " + permission);
        };
    }
}

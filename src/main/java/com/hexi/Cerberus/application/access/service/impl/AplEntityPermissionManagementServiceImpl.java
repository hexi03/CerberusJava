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
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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
public class AplEntityPermissionManagementServiceImpl implements EntityPermissionManagementService {
    public final DepartmentRepository departmentRepository;
    public final FactorySiteRepository factorySiteRepository;
    public final WareHouseRepository wareHouseRepository;
    public final PermissionRegistry permissionRegistry = PermissionRegistry.getInstance();

    public final MutableAclService aclService;

    @SneakyThrows
    @Override
    public List<Permission> displayPermissions(EntityID resourceId, GroupID groupId) {
        switch (resourceId) {
            case DepartmentID depId:
                Optional<Department> department = departmentRepository.findById(depId);
                if (department.isEmpty())
                    throw new Exception(String.format("There is no department with ID(%s)", resourceId.getId()));
                return department.get().getPermissions(aclService, getSid(groupId));
            case FactorySiteID facId:
                Optional<FactorySite> factorySite = factorySiteRepository.findById(facId);
                if (factorySite.isEmpty())
                    throw new Exception(String.format("There is no department with ID(%s)", resourceId.getId()));
                return factorySite.get().getPermissions(aclService, getSid(groupId));
            case WareHouseID whId:
                Optional<WareHouse> wareHouse = wareHouseRepository.findById(whId);
                if (wareHouse.isEmpty())
                    throw new Exception(String.format("There is no department with ID(%s)", resourceId.getId()));
                return wareHouse.get().getPermissions(aclService, getSid(groupId));
            default:

                throw new Exception(String.format("Have no case for %s", resourceId.getClass().getName()));
        }
    }

    private Sid getSid(GroupID accessorId) {
        return new PrincipalSid(accessorId.getId().toString());
    }

    @Override
    public List<AccessUnit> getAccessInfo(EntityID resourceId) throws Exception {
        switch (resourceId) {
            case DepartmentID depId:
                Optional<Department> department = departmentRepository.findById(depId);
                department.orElseThrow(() -> new Exception(String.format("There is no department with ID(%s)", resourceId.getId())));
                return fetchAccessUnits(department.get());
            case FactorySiteID facId:
                Optional<FactorySite> factorySite = factorySiteRepository.findById(facId);
                factorySite.orElseThrow(() -> new Exception(String.format("There is no factorySite with ID(%s)", resourceId.getId())));
                return fetchAccessUnits(factorySite.get());
            case WareHouseID whId:
                Optional<WareHouse> wareHouse = wareHouseRepository.findById(whId);
                wareHouse.orElseThrow(() -> new Exception(String.format("There is no wareHouse with ID(%s)", resourceId.getId())));
                return fetchAccessUnits(wareHouse.get());
            default:

                throw new Exception(String.format("Have no case for %s", resourceId.getClass().getName()));
        }
    }

    private List<AccessUnit> fetchAccessUnits(SecuredEntity entity) {
        return entity
                .getPermissions(aclService)
                .entrySet()
                .stream()
                .map(
                        sidListEntry ->
                                AccessUnit
                                        .builder()
                                        .resourceId(entity.getId())
                                        .accessorId(new GroupID(sidListEntry.getKey().toString()))
                                        .permissions(
                                                sidListEntry
                                                        .getValue()
                                                        .stream()
                                                        .map(permission ->
                                                                permission instanceof BehavioredPermissionFactory.BehavioredPermission ? permissionRegistry.getPermissionNamePure((BehavioredPermissionFactory.BehavioredPermission) permission) : permissionRegistry.getPermissionName(permission))
                                                        .filter(s -> s.isPresent())
                                                        .map(s -> s.get())
                                                        .collect(Collectors.toList())
                                        ).build()
                ).toList();
    }

    @Override
    public void setPermissions(AccessUnit accessUnit) throws Exception {
        List<Permission> permissions;
        switch (accessUnit.getResourceId()) {
            case DepartmentID depId:
                Optional<Department> department = departmentRepository.findById(depId);
                if (department.isEmpty())
                    throw new Exception(String.format("There is no department with ID(%s)", accessUnit.getResourceId().getId()));
                permissions = accessUnit.getPermissions().stream().map(s -> switch (s) {
                            case "READ" -> BehavioredPermissionFactory.READ;
                            case "MODIFY" -> BehavioredPermissionFactory.MODIFY_INHERITABLE;
                            default -> throw new RuntimeException("Fake permission");
                        }
                ).collect(Collectors.toList());
                department.get().updatePermissions(aclService, getSid(accessUnit.getAccessorId()), permissions);
                return;
            case FactorySiteID facId:
                Optional<FactorySite> factorySite = factorySiteRepository.findById(facId);
                if (factorySite.isEmpty())
                    throw new Exception(String.format("There is no department with ID(%s)", accessUnit.getResourceId().getId()));
                permissions = accessUnit.getPermissions().stream().map(s -> switch (s) {
                            case "READ" -> BehavioredPermissionFactory.READ;
                            case "MODIFY" -> BehavioredPermissionFactory.MODIFY;
                            default -> throw new RuntimeException("Fake permission");
                        }
                ).collect(Collectors.toList());
                factorySite.get().updatePermissions(aclService, getSid(accessUnit.getAccessorId()), permissions);
                return;
            case WareHouseID whId:
                Optional<WareHouse> wareHouse = wareHouseRepository.findById(whId);
                if (wareHouse.isEmpty())
                    throw new Exception(String.format("There is no department with ID(%s)", accessUnit.getResourceId().getId()));
                permissions = accessUnit.getPermissions().stream().map(s -> switch (s) {
                            case "READ" -> BehavioredPermissionFactory.READ;
                            case "MODIFY" -> BehavioredPermissionFactory.MODIFY;
                            default -> throw new RuntimeException("Fake permission");
                        }
                ).collect(Collectors.toList());
                wareHouse.get().updatePermissions(aclService, getSid(accessUnit.getAccessorId()), permissions);
                return;
            default:

                throw new Exception(String.format("Have no case for %s", accessUnit.getResourceId().getClass().getName()));
        }
    }
}

package com.hexi.Cerberus.domain.access;

import org.springframework.security.acls.model.Permission;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PermissionRegistry {
    static PermissionRegistry instance;
    Map<String, Permission> registered = new HashMap<>();

    PermissionRegistry() {

    }

    public static PermissionRegistry getInstance() {
        if (instance == null) instance = new PermissionRegistry();
        return instance;
    }

    public Optional<Permission> getPermission(String name) {
        return Optional.ofNullable(registered.get(name));
    }

    public Optional<String> getPermissionName(Permission permission) {
        int mask = permission.getMask();
        return
                registered
                        .entrySet()
                        .stream()
                        .filter(stringPermissionEntry -> stringPermissionEntry.getValue().getMask() == mask)
                        .map(stringPermissionEntry -> stringPermissionEntry.getKey())
                        .findAny();
    }

    public Optional<String> getPermissionNamePure(BehavioredPermissionFactory.BehavioredPermission permission) {
        int mask = permission.getMask();
        return
                registered
                        .entrySet()
                        .stream()
                        .filter(stringPermissionEntry -> stringPermissionEntry.getValue() instanceof BehavioredPermissionFactory.BehavioredPermission)
                        .filter(stringPermissionEntry -> ((BehavioredPermissionFactory.BehavioredPermission) stringPermissionEntry.getValue()).getPure().getMask() == mask)
                        .map(stringPermissionEntry -> stringPermissionEntry.getKey())
                        .findAny();
    }

    public Permission registerPermission(String permissionName, Permission perm) {

        Assert.notNull(perm, "Permission required");
        Assert.hasText(permissionName, "Permission name required");

        Assert.isTrue(!getPermission(permissionName).isPresent(),
                () -> "An existing Permission already provides name '" + permissionName + "'");
        registered.put(permissionName, perm);
        return perm;
    }
}

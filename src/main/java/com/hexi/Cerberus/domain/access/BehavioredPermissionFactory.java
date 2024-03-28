package com.hexi.Cerberus.domain.access;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.acls.domain.DefaultPermissionFactory;
import org.springframework.security.acls.domain.PermissionFactory;
import org.springframework.security.acls.model.Permission;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BehavioredPermissionFactory implements PermissionFactory {



    public static final BehavioredPermission READ = (BehavioredPermission) PermissionRegistry.getInstance().registerPermission(
            "READ",
            new BehavioredPermissionImpl(0, Behavior.builder().inheritable(false).build())
    );

    public static final BehavioredPermission READ_INHERITABLE = (BehavioredPermission) PermissionRegistry.getInstance().registerPermission(
            "READ_INHERITABLE",
            new BehavioredPermissionImpl(1, Behavior.builder().inheritable(true).build())
    );

    public static final BehavioredPermission MODIFY = (BehavioredPermission) PermissionRegistry.getInstance().registerPermission(
            "MODIFY",
            new BehavioredPermissionImpl(2, Behavior.builder().inheritable(false).build())
    );

    public static final BehavioredPermission MODIFY_INHERITABLE = (BehavioredPermission) PermissionRegistry.getInstance().registerPermission(
            "MODIFY_INHERITABLE",
            new BehavioredPermissionImpl(3, Behavior.builder().inheritable(true).build())
    );


    static final PermissionRegistry permissionRegistry = PermissionRegistry.getInstance();

    @Builder
    @Getter
    public static class Behavior{
        enum BehaviourBit{
            INHERITABLE(0);

            final int bit;
            BehaviourBit(int bit){
                this.bit = bit;
            }
        }
        public final boolean inheritable;

        public Behavior(long mask){
            inheritable = (mask & (1 << BehaviourBit.INHERITABLE.bit)) == 1;
        }

        public int getMask() {
            int mask = 0;
            mask += (inheritable ? 1 : 0) << (BehaviourBit.INHERITABLE.bit);
            return mask;
        }
    }

    public interface BehavioredPermission extends Permission{
        Behavior getBehaviour();
        Permission getPure();
    }

    public static class BehavioredPermissionImpl implements BehavioredPermission {



        final int behaviorShift = 8;
        private final int mask;


        public BehavioredPermissionImpl(int number, Behavior behavior) {

            this.mask = makeMask(number, behavior);
        }

        BehavioredPermissionImpl(int mask) {

            this.mask = mask;
        }

        private int makeMask(int number, Behavior behavior) {
            return behavior.getMask() + number << behaviorShift;
        }

        public int getMask() {
            return mask;
        }

        @Override
        public String getPattern() {
            return null;
        }

        @Override
        public Behavior getBehaviour() {
            return new Behavior(mask & (1 << behaviorShift - 1));
        }

        @Override
        public Permission getPure() {
            return new DefaultPermissionFactory().buildFromMask(mask >> behaviorShift);
        }



    }

    @Override
    public Permission buildFromMask(int mask) {
        return new BehavioredPermissionImpl(mask);
    }

    @Override
    public Permission buildFromName(String name) {
        return permissionRegistry.getPermission(name).orElseThrow(() -> new RuntimeException("Fake permission name"));
    }

    @Override
    public List<Permission> buildFromNames(List<String> names) {
        return names.stream().map(this::buildFromName).collect(Collectors.toList());
    }
}

package com.hexi.Cerberus.domain.access;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.log.LogMessage;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.acls.AclPermissionEvaluator;
import org.springframework.security.acls.domain.DefaultPermissionFactory;
import org.springframework.security.acls.domain.ObjectIdentityRetrievalStrategyImpl;
import org.springframework.security.acls.domain.PermissionFactory;
import org.springframework.security.acls.domain.SidRetrievalStrategyImpl;
import org.springframework.security.acls.model.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Slf4j
@RequiredArgsConstructor
public class BehavioredAclPermissionEvaluator implements PermissionEvaluator {

    private final AclService aclService;

    private ObjectIdentityRetrievalStrategy objectIdentityRetrievalStrategy = new ObjectIdentityRetrievalStrategyImpl();

    private ObjectIdentityGenerator objectIdentityGenerator = new ObjectIdentityRetrievalStrategyImpl();

    private SidRetrievalStrategy sidRetrievalStrategy = new SidRetrievalStrategyImpl();

    private PermissionFactory permissionFactory = new BehavioredPermissionFactory();


    /**
     * Determines whether the user has the given permission(s) on the domain object using
     * the ACL configuration. If the domain object is null, returns false (this can always
     * be overridden using a null check in the expression itself).
     */
    public boolean hasPermission(Authentication authentication, Object domainObject, Object permission) {
        if (domainObject == null) {
            return false;
        }
        ObjectIdentity objectIdentity = this.objectIdentityRetrievalStrategy.getObjectIdentity(domainObject);
        return checkPermission(authentication, objectIdentity, permission);
    }

    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
                                 Object permission) {
        ObjectIdentity objectIdentity = this.objectIdentityGenerator.createObjectIdentity(targetId, targetType);
        return checkPermission(authentication, objectIdentity, permission);
    }


    List<Permission> resolvePermission(Object permission) {
        if (permission instanceof Integer) {
            return Arrays.asList(this.permissionFactory.buildFromMask((Integer) permission));
        }
        if (permission instanceof Permission) {
            return Arrays.asList((Permission) permission);
        }
        if (permission instanceof Permission[]) {
            return Arrays.asList((Permission[]) permission);
        }
        if (permission instanceof String permString) {
            Permission p = buildPermission(permString);
            if (p != null) {
                return Arrays.asList(p);
            }
        }

        throw new IllegalArgumentException("Unsupported permission: " + permission);
    }

    private Permission buildPermission(String permString) {
        try {
            return this.permissionFactory.buildFromName(permString);
        }
        catch (IllegalArgumentException notfound) {
            return this.permissionFactory.buildFromName(permString.toUpperCase(Locale.ENGLISH));
        }
    }

    public void setObjectIdentityRetrievalStrategy(ObjectIdentityRetrievalStrategy objectIdentityRetrievalStrategy) {
        this.objectIdentityRetrievalStrategy = objectIdentityRetrievalStrategy;
    }

    public void setObjectIdentityGenerator(ObjectIdentityGenerator objectIdentityGenerator) {
        this.objectIdentityGenerator = objectIdentityGenerator;
    }

    public void setSidRetrievalStrategy(SidRetrievalStrategy sidRetrievalStrategy) {
        this.sidRetrievalStrategy = sidRetrievalStrategy;
    }





    private boolean checkPermission(Authentication authentication, ObjectIdentity oid, Object permission) {
        List<Sid> sids = this.sidRetrievalStrategy.getSids(authentication);
        List<Permission> requiredPermission = this.resolvePermission(permission);
        this.log.debug(LogMessage.of(() -> {
            return "Checking permission '" + permission + "' for object '" + oid + "'";
        }).toString());

        try {
            Acl acl = this.aclService.readAclById(oid, sids);
            if (acl.isGranted(requiredPermission, sids, false)) {
                this.log.debug("Access is granted");
                return true;
            }

            this.log.debug("Returning false - ACLs returned, but insufficient permissions for this principal");
        } catch (NotFoundException var7) {
            this.log.debug("Returning false - no ACLs apply for this principal");
        }

        return false;
    }
}

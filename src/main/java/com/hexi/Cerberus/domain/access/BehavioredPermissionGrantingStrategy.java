package com.hexi.Cerberus.domain.access;


import org.springframework.security.acls.domain.AuditLogger;
import org.springframework.security.acls.model.*;
import org.springframework.util.Assert;

import java.util.List;

public class BehavioredPermissionGrantingStrategy implements PermissionGrantingStrategy {

    private final transient AuditLogger auditLogger;

    /**
     * Creates an instance with the logger which will be used to record granting and
     * denial of requested permissions.
     */
    public BehavioredPermissionGrantingStrategy(AuditLogger auditLogger) {
        Assert.notNull(auditLogger, "auditLogger cannot be null");
        this.auditLogger = auditLogger;
    }

    @Override
    public boolean isGranted(Acl acl, List<Permission> permission, List<Sid> sids, boolean administrativeMode)
    {
        return isGranted1(acl, permission, sids, administrativeMode, false);
    }

    public boolean isGranted1(Acl acl, List<Permission> permission, List<Sid> sids, boolean administrativeMode, boolean parent)
            throws NotFoundException {
        List<AccessControlEntry> aces = acl.getEntries();
        AccessControlEntry firstRejection = null;
        for (Permission p : permission) {
            for (Sid sid : sids) {
                // Attempt to find exact match for this permission mask and SID
                boolean scanNextSid = true;
                for (AccessControlEntry ace : aces) {
                    if (isGranted1(ace, p, parent) && ace.getSid().equals(sid)) {
                        // Found a matching ACE, so its authorization decision will
                        // prevail
                        if (ace.isGranting()) {
                            // Success
                            if (!administrativeMode) {
                                this.auditLogger.logIfNeeded(true, ace);
                            }
                            return true;
                        }

                        // Failure for this permission, so stop search
                        // We will see if they have a different permission
                        // (this permission is 100% rejected for this SID)
                        if (firstRejection == null) {
                            // Store first rejection for auditing reasons
                            firstRejection = ace;
                        }
                        scanNextSid = false; // helps break the loop

                        break; // exit aces loop
                    }
                }
                if (!scanNextSid) {
                    break; // exit SID for loop (now try next permission)
                }
            }
        }

        if (firstRejection != null) {
            // We found an ACE to reject the request at this point, as no
            // other ACEs were found that granted a different permission
            if (!administrativeMode) {
                this.auditLogger.logIfNeeded(false, firstRejection);
            }
            return false;
        }

        // No matches have been found so far
        if (acl.getParentAcl() != null) {
            // We have a parent, so let them try to find a matching ACE
            return isGranted1(acl.getParentAcl(), permission, sids, false, true);
        }

        // We either have no parent, or we're the uppermost parent
        throw new NotFoundException("Unable to locate a matching ACE for passed permissions and SIDs");
    }

    protected boolean isGranted1(AccessControlEntry ace, Permission p, boolean parent) {
        switch (ace.getPermission()){
            case BehavioredPermissionFactory.BehavioredPermission bhPerm:
                return bhPerm.getPure().getMask() == (p instanceof BehavioredPermissionFactory.BehavioredPermission ? ((BehavioredPermissionFactory.BehavioredPermission)p).getPure().getMask() : p.getMask()) &&
                    (
                            !parent || bhPerm.getBehaviour().isInheritable()

                    );
            default:
                return (ace.getPermission().getMask() == p.getMask()) && !parent;
        }


    }

}

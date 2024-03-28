package com.hexi.Cerberus.infrastructure.entity;

import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.model.*;
import org.springframework.security.core.userdetails.User;

import java.util.*;
import java.util.stream.Collectors;

public interface SecuredEntity extends Entity{

    default void initAcl(MutableAclService aclService){
        aclService.createAcl(getObjectIdentity());
    }
    default ObjectIdentity getObjectIdentity(){
        return new ObjectIdentityImpl(this.getClass(), this.getId().toString());
    }
    static ObjectIdentity getObjectIdentity(Class<Entity> cls, EntityId id){
        return new ObjectIdentityImpl(cls, id.toString());
    }
    default void updatePermissions(MutableAclService aclService, Sid sid, List<Permission> perms){
        ObjectIdentity oid = this.getObjectIdentity();
        MutableAcl acl = (MutableAcl) aclService.readAclById(oid);


        //clear
        int size = acl.getEntries().size();
        for (int i = size - 1; i >= 0; i--) {
            acl.deleteAce(i);
        }

        //permiting
        for (Permission perm : perms)
            acl.insertAce(acl.getEntries().size(), perm, sid, true);
        aclService.updateAcl(acl);
    };

    default void addPermission(MutableAclService aclService, Sid sid, Permission perm){
        ObjectIdentity oid = this.getObjectIdentity();
        MutableAcl acl = (MutableAcl) aclService.readAclById(oid);

        //permiting
            acl.insertAce(acl.getEntries().size(), perm, sid, true);
        aclService.updateAcl(acl);
    };
    default List<Permission> getPermissions(AclService aclService, Sid sid){
        ObjectIdentity oid = new ObjectIdentityImpl(this.getClass(), this.getId().toString());
        Acl acl = aclService.readAclById(oid);

        return acl.getEntries().stream().filter((ace) -> ace.getSid().equals(sid)).map((ace) -> ace.getPermission()).collect(Collectors.toList());
    }

    default Map<Sid,List<Permission>> getPermissions(AclService aclService){
        ObjectIdentity oid = new ObjectIdentityImpl(this.getClass(), this.getId().toString());
        Acl acl = aclService.readAclById(oid);

        return acl.getEntries().stream().reduce(new HashMap<Sid,List<Permission>>(),
                (acc, ace) -> {
                    acc.put(ace.getSid(), Arrays.asList(ace.getPermission())); return acc;},
                (c1, c2) -> {
                    c2.forEach(
                            (key,value) ->
                                    c1.merge(key, value, (v1,v2) -> {v1.addAll(v2); return v1;
                                    })); return c1;
        });
    }

    default boolean hasPermission(AclService aclService, Sid sid, Permission permission){
        ObjectIdentity oid = new ObjectIdentityImpl(this.getClass(), this.getId().toString());
        Acl acl = aclService.readAclById(oid);

        return acl
                .getEntries()
                .stream()
                .filter((ace) -> ace.getSid().equals(sid))
                .map((ace) -> ace.getPermission())
                .filter((permission1 -> permission1.equals(permission)))
                .findAny()
                .isPresent();
    }

    default void purgePermissionsRecursively(MutableAclService aclService){
        ObjectIdentity oid = this.getObjectIdentity();
        aclService.deleteAcl(oid, true);
    }

    default void setParent(MutableAclService aclService, ObjectIdentity parent){
        ObjectIdentity oid = new ObjectIdentityImpl(this.getClass(), this.getId().toString());
        MutableAcl acl = (MutableAcl) aclService.readAclById(oid);
        Acl parentAcl = aclService.readAclById(parent);
        acl.setParent(parentAcl);
    }

    default boolean hasAccess(MutableAclService aclService, Sid sid, List<Permission> perms){
        ObjectIdentity oid = this.getObjectIdentity();

        Acl acl = aclService.readAclById(oid);
        return acl.isGranted(perms, Arrays.stream((new Sid[]{sid})).toList(),false);
    }





}

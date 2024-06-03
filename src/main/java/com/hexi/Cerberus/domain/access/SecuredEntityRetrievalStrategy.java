package com.hexi.Cerberus.domain.access;

import com.hexi.Cerberus.infrastructure.entity.SecuredEntity;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.ObjectIdentityRetrievalStrategy;

public class SecuredEntityRetrievalStrategy implements ObjectIdentityRetrievalStrategy {
    @Override
    public ObjectIdentity getObjectIdentity(Object domainObject) {
        if (domainObject instanceof SecuredEntity) {
            SecuredEntity securedEntity = (SecuredEntity) domainObject;
            return securedEntity.getObjectIdentity();
        }
        throw new RuntimeException("Object is not a SecuredEntity");
    }
}

package com.hexi.Cerberus.domain.access;

import com.hexi.Cerberus.domain.department.Department;
import com.hexi.Cerberus.infrastructure.entity.SecuredEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
public class ACLSupply {
    @PostFilter("hasPermission(filterObject, 'READ')")
    public Collection<Department> filterACL(List<Department> allWithQuery) {
        return allWithQuery;
    }

    @PostAuthorize("returnObject.orElse(null) == null or hasPermission(returnObject.get(), 'READ')")
    public Optional<SecuredEntity> filterACL(Optional<SecuredEntity> sec) {
        return sec;
    }

    @PostAuthorize("returnObject == null or hasPermission(returnObject, 'READ')")
    public SecuredEntity filterACL(SecuredEntity sec) {
        return sec;
    }
}

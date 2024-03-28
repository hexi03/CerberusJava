package com.hexi.Cerberus.domain.access;

import com.hexi.Cerberus.domain.group.GroupID;
import com.hexi.Cerberus.infrastructure.entity.EntityId;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.acls.model.Permission;

import java.util.List;

@Getter
@Builder
public class AccessUnit {
    EntityId resourceId;
    GroupID accessorId;
    List<String> permissions;
}

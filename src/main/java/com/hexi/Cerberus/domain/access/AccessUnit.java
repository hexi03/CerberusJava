package com.hexi.Cerberus.domain.access;

import com.hexi.Cerberus.domain.group.GroupID;
import com.hexi.Cerberus.infrastructure.entity.EntityID;
import com.hexi.Cerberus.infrastructure.entity.UUIDBasedEntityID;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class AccessUnit {
    EntityID resourceId;
    UUIDBasedEntityID accessorId;
    List<String> permissions;
}

package com.hexi.Cerberus.adapter.web.rest.access.DTO;

import com.hexi.Cerberus.infrastructure.entity.UUIDBasedEntityID;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ModifyAccessRequest {
    String resourceType;
    UUIDBasedEntityID resourceId;
    UUIDBasedEntityID accessorId;
    List<String> permissions;
}

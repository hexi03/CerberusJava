package com.hexi.Cerberus.adapter.web.rest.access.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ModifyAccessRequest {
    String resourceType;
    String resourceId;
    String accessorId;
    List<String> premissions;
}

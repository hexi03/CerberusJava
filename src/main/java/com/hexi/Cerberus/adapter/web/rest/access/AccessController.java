package com.hexi.Cerberus.adapter.web.rest.access;

import com.hexi.Cerberus.adapter.web.rest.access.DTO.ModifyAccessRequest;
import com.hexi.Cerberus.adapter.web.rest.UserGroup.DomainToDTOMapper;
import com.hexi.Cerberus.application.access.service.EntityPermissionManagementService;
import com.hexi.Cerberus.domain.access.AccessUnit;
import com.hexi.Cerberus.domain.department.DepartmentID;
import com.hexi.Cerberus.domain.factorysite.FactorySiteID;
import com.hexi.Cerberus.domain.group.GroupID;
import com.hexi.Cerberus.domain.warehouse.WareHouseID;
import com.hexi.Cerberus.infrastructure.adapter.DrivingAdapter;
import com.hexi.Cerberus.infrastructure.entity.EntityId;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
@DrivingAdapter
@RequestMapping("/api/access")
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@AllArgsConstructor
@Slf4j
public class AccessController {
    public final EntityPermissionManagementService accessManagementService;
    public final DomainToDTOMapper domainToDTOMapper;


    @PostMapping("/modifyAccess")
    public ResponseEntity<Void> modifyAccess(@RequestBody ModifyAccessRequest dto) {

        EntityId resourceId;
        switch (dto.getResourceId()){
            case "department":
                resourceId = new DepartmentID(UUID.fromString(dto.getResourceId()));
                break;
            case "factorySite":
                resourceId = new FactorySiteID(UUID.fromString(dto.getResourceId()));
                break;
            case "wareHouse":
                resourceId = new WareHouseID(UUID.fromString(dto.getResourceId()));
                break;
            default:
                return ResponseEntity.notFound().build();
        }


        GroupID accessorId = new GroupID(UUID.fromString(dto.getResourceId()));
//        switch (dto.getResourceId()){
//            case "user":
//                accessorId = new UserID(UUID.fromString(dto.getResourceId()));
//                break;
//            case "group":
//                accessorId = ;
//                break;
//            default:
//                return ResponseEntity.notFound().build();
//        }

        List<String> permissions =
                dto.getPremissions()
                        .stream()
//                        .map(s -> {switch (s) {
//                            case "READ":
//                                return BehavioredPermissionFactory.READ;
//                            case "MODIFY":
//                                return BehavioredPermissionFactory.MODIFY;
//                            default:
//                                return null;
//                        }}).filter(permission -> permission != null)

                        .filter(permission -> permission == "READ" || permission == "MODIFY")
                        .collect(Collectors.toList());

        try {
            accessManagementService.setPermissions(
                    AccessUnit
                            .builder()
                            .resourceId(resourceId)
                            .accessorId(accessorId)
                            .permissions(permissions)
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }




}
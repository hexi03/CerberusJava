package com.hexi.Cerberus.adapter.web.rest.access;

import com.hexi.Cerberus.adapter.web.rest.access.DTO.ModifyAccessRequest;
import com.hexi.Cerberus.application.access.service.EntityPermissionManagementService;
import com.hexi.Cerberus.application.user.service.UserDomainToDTOMapper;
import com.hexi.Cerberus.domain.access.AccessUnit;
import com.hexi.Cerberus.domain.department.DepartmentID;
import com.hexi.Cerberus.domain.factorysite.FactorySiteID;
import com.hexi.Cerberus.domain.group.GroupID;
import com.hexi.Cerberus.domain.warehouse.WareHouseID;
import com.hexi.Cerberus.infrastructure.adapter.DrivingAdapter;
import com.hexi.Cerberus.infrastructure.entity.EntityID;
import com.hexi.Cerberus.infrastructure.entity.UUIDBasedEntityID;
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
    public final UserDomainToDTOMapper userDomainToDTOMapper;


    @PostMapping("/modifyAccess")
    public ResponseEntity<Void> modifyAccess(@RequestBody ModifyAccessRequest dto) {

        EntityID resourceId;
        switch (dto.getResourceType()) {
            case "department":
                resourceId = new DepartmentID(dto.getResourceId());
                break;
            case "factorySite":
                resourceId = new FactorySiteID(dto.getResourceId());
                break;
            case "wareHouse":
                resourceId = new WareHouseID(dto.getResourceId());
                break;
            default:
                return ResponseEntity.status(422).build();
        }


        UUIDBasedEntityID accessorId = dto.getAccessorId();
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
        log.info("Raw permissions: " + dto.getPermissions());
        List<String> permissions =
                dto.getPermissions()
                        .stream()
//                        .map(s -> {switch (s) {
//                            case "READ":
//                                return BehavioredPermissionFactory.READ;
//                            case "MODIFY":
//                                return BehavioredPermissionFactory.MODIFY;
//                            default:
//                                return null;
//                        }}).filter(permission -> permission != null)

                        .filter(permission -> permission.equals("READ")  || permission.equals("MODIFY"))
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
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }


}
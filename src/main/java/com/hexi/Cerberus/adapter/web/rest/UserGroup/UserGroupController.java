package com.hexi.Cerberus.adapter.web.rest.UserGroup;

import com.hexi.Cerberus.adapter.web.rest.UserGroup.DTO.*;
import com.hexi.Cerberus.application.group.service.GroupManagementService;
import com.hexi.Cerberus.application.user.service.UserManagementService;
import com.hexi.Cerberus.domain.group.Group;
import com.hexi.Cerberus.domain.group.GroupID;
import com.hexi.Cerberus.domain.group.command.CreateGroupCmd;
import com.hexi.Cerberus.domain.group.command.GroupExcludeUsersCmd;
import com.hexi.Cerberus.domain.group.command.GroupIncludeUsersCmd;
import com.hexi.Cerberus.domain.group.command.UpdateGroupDetailsCmd;
import com.hexi.Cerberus.domain.user.User;
import com.hexi.Cerberus.domain.user.UserID;
import com.hexi.Cerberus.domain.user.command.CreateUserCmd;
import com.hexi.Cerberus.domain.user.command.UpdateUserDetailsCmd;
import com.hexi.Cerberus.infrastructure.adapter.DrivingAdapter;
import com.hexi.Cerberus.infrastructure.command.CommandId;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@DrivingAdapter
@RequestMapping("/api/usergroup")
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@AllArgsConstructor
@Slf4j
public class UserGroupController {
    public final UserManagementService userManagementService;
    public final GroupManagementService groupManagementService;
    public final DomainToDTOMapper domainToDTOMapper;

    //USER
    @GetMapping("/fetchUser")
    public ResponseEntity<List<UserDetailsDTO>> fetchUser(@PathVariable(required = false) UserID id) {
        if (id != null) {
            return ResponseEntity.ok(userManagementService
                    .displayAllBy()
                    .stream()
                    .map(domainToDTOMapper::mapUserToDetailsDto)
                    .collect(Collectors.toList())
            );
        } else {
            Optional<User> user = userManagementService.displayBy(id);
            if (user.isEmpty()) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(
                    List.of(
                            domainToDTOMapper.mapUserToDetailsDto(
                                    user.get()
                            )
                    )
            );
        }
    }

    @PostMapping("/addUser")
    public ResponseEntity<Void> addUser(CreateUserDTO dto) {
        userManagementService.create(
                CreateUserCmd
                        .builder()
                        .id(CommandId.generate())
                        .name(dto.getName())
                        .build()
        );
        return ResponseEntity.ok().build();

    }

    @PutMapping("/updateUser")
    public ResponseEntity<Void> updateUser(UserUpdateDetailsDTO dto) {
        userManagementService.updateDetails(
                UpdateUserDetailsCmd
                        .builder()
                        .id(CommandId.generate())
                        .userId(dto.getId())
                        .name(dto.getName())
                        .build()
        );
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/removeUser")
    public ResponseEntity<Void> removeUser(UserID id) {
        userManagementService.delete(id);
        return ResponseEntity.ok().build();
    }


    //GROUP
    @GetMapping("/fetchGroup")
    public ResponseEntity<List<GroupDetailsDTO>> fetchGroup(@PathVariable(required = false) GroupID id) {
        if (id != null) {
            return ResponseEntity.ok(groupManagementService
                    .displayAll()
                    .stream()
                    .map(domainToDTOMapper::mapGroupToDetailsDto)
                    .collect(Collectors.toList())
            );
        } else {
            Optional<Group> user = groupManagementService.displayBy(id);
            if (user.isEmpty()) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(
                    List.of(
                            domainToDTOMapper.mapGroupToDetailsDto(
                                    user.get()
                            )
                    )
            );
        }
    }

    @PostMapping("/addGroup")
    public ResponseEntity<Void> addGroup(CreateGroupDTO dto) {
        groupManagementService.create(
                CreateGroupCmd
                        .builder()
                        .id(CommandId.generate())
                        .name(dto.getName())
                        .build()
        );
        return ResponseEntity.ok().build();
    }

    @PutMapping("/updateGroup")
    public ResponseEntity<Void> updateGroup(GroupUpdateDetailsDTO dto) {
        groupManagementService.updateDetails(
                UpdateGroupDetailsCmd
                        .builder()
                        .id(CommandId.generate())
                        .groupId(dto.getId())
                        .name(dto.getName())
                        .build()
        );
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/removeGroup")
    public ResponseEntity<Void> removeGroup(GroupID id) {
        groupManagementService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/includeUsers")
    public ResponseEntity<Void> updateGroup(GroupMembersModificationDTO dto) {
        if (dto.isExclude()) {
            groupManagementService.excludeUsers(
                    GroupExcludeUsersCmd
                            .builder()
                            .id(CommandId.generate())
                            .groupId(dto.getId())
                            .users(dto.getUsers())
                            .build()
            );
        } else {
            groupManagementService.includeUsers(
                    GroupIncludeUsersCmd
                            .builder()
                            .id(CommandId.generate())
                            .groupId(dto.getId())
                            .users(dto.getUsers())
                            .build()
            );
        }

        return ResponseEntity.ok().build();
    }


}

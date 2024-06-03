package com.hexi.Cerberus.adapter.web.rest.UserGroup;

import com.hexi.Cerberus.application.group.service.DTO.CreateGroupDTO;
import com.hexi.Cerberus.application.group.service.DTO.GroupDetailsDTO;
import com.hexi.Cerberus.application.group.service.DTO.GroupMembersModificationDTO;
import com.hexi.Cerberus.application.group.service.DTO.GroupUpdateDetailsDTO;
import com.hexi.Cerberus.application.group.service.GroupManagementService;
import com.hexi.Cerberus.application.user.service.DTO.CreateUserDTO;
import com.hexi.Cerberus.application.user.service.DTO.UserDetailsDTO;
import com.hexi.Cerberus.application.user.service.DTO.UserUpdateDetailsDTO;
import com.hexi.Cerberus.application.user.service.UserDomainToDTOMapper;
import com.hexi.Cerberus.application.user.service.UserManagementService;
import com.hexi.Cerberus.domain.group.GroupID;
import com.hexi.Cerberus.application.group.service.command.CreateGroupCmd;
import com.hexi.Cerberus.application.group.service.command.GroupSetUsersCmd;
import com.hexi.Cerberus.application.group.service.command.UpdateGroupDetailsCmd;
import com.hexi.Cerberus.domain.user.UserID;
import com.hexi.Cerberus.application.user.service.command.CreateUserCmd;
import com.hexi.Cerberus.application.user.service.command.UpdateUserDetailsCmd;
import com.hexi.Cerberus.domain.warehouse.exception.WareHouseAccessDeniedException;
import com.hexi.Cerberus.domain.warehouse.exception.WareHouseNotFoundException;
import com.hexi.Cerberus.infrastructure.adapter.DrivingAdapter;
import com.hexi.Cerberus.infrastructure.command.CommandId;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@DrivingAdapter
@RequestMapping("/api/usergroup")
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@AllArgsConstructor
@Slf4j
public class UserGroupController {
    public final UserManagementService userManagementService;
    public final GroupManagementService groupManagementService;
    public final UserDomainToDTOMapper domainToDTOMapper;

    //USER
    @GetMapping("/fetchUser")
    public ResponseEntity<List<UserDetailsDTO>> fetchUser(@RequestParam(required = false)  UserID id) {
        if (id == null) {
            return ResponseEntity.ok(userManagementService
                    .displayAll()
            );
        } else {
            Optional<UserDetailsDTO> user = userManagementService.displayBy(id);
            if (user.isEmpty()) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(
                    List.of(
                            user.get()
                    )
            );
        }
    }

    @PostMapping("/addUser")
    public ResponseEntity<UserID> addUser(@RequestBody  CreateUserDTO dto) {
        UserID id = userManagementService.create(
                CreateUserCmd
                        .builder()
                        .id(CommandId.generate())
                        .name(dto.getName())
                        .email(dto.getEmail())
                        .password(dto.getPassword())
                        .build()
        ).getId();
        return ResponseEntity.status(HttpStatus.CREATED).body(id);

    }

    @PutMapping("/updateUser")
    public ResponseEntity<Void> updateUser(@RequestBody UserUpdateDetailsDTO dto) {
        userManagementService.updateDetails(
                UpdateUserDetailsCmd
                        .builder()
                        .id(CommandId.generate())
                        .userId(dto.getId())
                        .name(dto.getName())
                        .email(dto.getEmail())
                        .password(dto.getPassword())
                        .build()
        );
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/removeUser")
    public ResponseEntity<Void> removeUser(@RequestParam UserID id) {
        userManagementService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    //GROUP
    @GetMapping("/fetchGroup")
    public ResponseEntity<List<GroupDetailsDTO>> fetchGroup(@RequestParam(required = false)  GroupID id) {
        if (id == null) {
            return ResponseEntity.ok(groupManagementService
                    .displayAll()
            );
        } else {
            Optional<GroupDetailsDTO> user = groupManagementService.displayBy(id);
            if (user.isEmpty()) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(
                    List.of(user.get())
            );
        }
    }

    @PostMapping("/addGroup")
    public ResponseEntity<GroupID> addGroup(@RequestBody CreateGroupDTO dto) {
        log.info(dto.toString());
        GroupID id = groupManagementService.create(
                CreateGroupCmd
                        .builder()
                        .id(CommandId.generate())
                        .name(dto.getName())
                        .build()
        ).getId();
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @PutMapping("/updateGroup")
    public ResponseEntity<Void> updateGroup(@RequestBody GroupUpdateDetailsDTO dto) {
        groupManagementService.updateDetails(
                UpdateGroupDetailsCmd
                        .builder()
                        .id(CommandId.generate())
                        .groupId(dto.getId())
                        .name(dto.getName())
                        .build()
        );
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/removeGroup")
    public ResponseEntity<Void> removeGroup(@RequestParam GroupID id) {
        groupManagementService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/setUsers")
    public ResponseEntity<Void> updateGroup(@RequestBody GroupMembersModificationDTO dto) {

            groupManagementService.setUsers(
                    GroupSetUsersCmd
                            .builder()
                            .id(CommandId.generate())
                            .groupId(dto.getId())
                            .users(dto.getUsers())
                            .build()
            );


        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


//    @ExceptionHandler(UserNotFoundException.class)
//    @ExceptionHandler(GroupNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleWareHouseNotFoundException(WareHouseNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

//    @ExceptionHandler(UserAccessDeniedException.class)
//    @ExceptionHandler(GroupAccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<String> handleWareHouseAccessDeniedException(WareHouseAccessDeniedException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

package com.hexi.Cerberus.adapter.web.rest.UserGroup;

import com.hexi.Cerberus.adapter.web.rest.UserGroup.DTO.GroupDetailsDTO;
import com.hexi.Cerberus.adapter.web.rest.UserGroup.DTO.UserDetailsDTO;
import com.hexi.Cerberus.domain.group.Group;
import com.hexi.Cerberus.domain.user.User;
import org.mapstruct.Mapper;

import java.util.stream.Collectors;

@Mapper
public interface DomainToDTOMapper {

    default UserDetailsDTO mapUserToDetailsDto(User user) {
        return UserDetailsDTO
                .builder()
                .id(user.getId())
                .groups(
                        user
                                .getGroups()
                                .stream()
                                .map(group -> group.getId())
                                .collect(Collectors.toList())
                )
                .name(user.getName())
                .build();
    }

    ;

    default GroupDetailsDTO mapGroupToDetailsDto(Group group) {
        return GroupDetailsDTO
                .builder()
                .id(group.getId())
                .users(
                        group
                                .getUsers()
                                .stream()
                                .map(user -> user.getId())
                                .collect(Collectors.toList())
                )
                .name(group.getName())
                .build();
    }

    ;
}

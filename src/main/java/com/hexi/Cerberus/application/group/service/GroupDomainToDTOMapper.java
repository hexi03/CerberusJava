package com.hexi.Cerberus.application.group.service;

import com.hexi.Cerberus.application.group.service.DTO.GroupDetailsDTO;
import com.hexi.Cerberus.application.user.service.DTO.UserDetailsDTO;
import com.hexi.Cerberus.domain.group.Group;
import com.hexi.Cerberus.domain.user.User;
import org.mapstruct.Mapper;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface GroupDomainToDTOMapper {
    default GroupDetailsDTO groupToDetailsDTO(Group group) {
        return GroupDetailsDTO
                .builder()
                .id(group.getId())
//                .users(
//                        group
//                                .getUsers()
//                                .stream()
//                                .map(user -> user.getId())
//                                .collect(Collectors.toList())
//                )
                .name(group.getName())
                .build();
    }
}
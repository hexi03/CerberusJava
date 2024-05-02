package com.hexi.Cerberus.application.user.service;

import com.hexi.Cerberus.application.user.service.DTO.UserDetailsDTO;
import com.hexi.Cerberus.domain.user.User;
import org.mapstruct.Mapper;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserDomainToDTOMapper {

    default UserDetailsDTO userToDetailsDTO(User user) {
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

}

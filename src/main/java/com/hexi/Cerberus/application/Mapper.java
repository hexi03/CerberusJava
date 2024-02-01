package com.hexi.Cerberus.application;

import com.hexi.Cerberus.adapter.web.webstatic.UserGroupController.ViewModels.*;
import com.hexi.Cerberus.application.DTO.DepartmentDTO;
import com.hexi.Cerberus.application.DTO.FactorySiteDTO;
import com.hexi.Cerberus.application.DTO.WareHouseDTO;
import com.hexi.Cerberus.domain.aggregates.UserGroups;
import com.hexi.Cerberus.domain.entities.*;

import java.util.List;
import java.util.stream.Collectors;

@org.mapstruct.Mapper
public interface Mapper {
    User mapUserGroups(UserCreateModel user);

    Group mapGroup(GroupCreateModel user);

    GroupDetailsModel mapGroupDetailsModel(Group user);

    Group mapGroup(GroupEditModel group);

    User mapUserGroups(UserEditModel user);

    default UserDetailsModel mapUserDetailsModel(UserGroups user){
        UserDetailsModel model =
                UserDetailsModel.builder()
                        .id(user.getUser().getId())
                        .email(user.getUser().getEmail())
                        .name(user.getUser().getName())
                        .groupModels(user.getGroups().stream().map(this::mapUserDetailsGroupModel).collect(Collectors.toList()))
                        .build();
        return model;
    }

    UserDetailsModel.GroupModel mapUserDetailsGroupModel(Group group);

    default UserEditModel mapUserEditModel(UserGroups user){
        UserEditModel model =
                UserEditModel.builder()
                        .id(user.getUser().getId())
                        .email(user.getUser().getEmail())
                        .name(user.getUser().getName())
                        .groups(user.getGroups().stream().map(group -> group.getId()).collect(Collectors.toList()))
                        .groupModels(user.getGroups().stream().map(this::mapUserEditGroupModel).collect(Collectors.toList()))
                        .build();
        return model;
    }

    UserEditModel.GroupModel mapUserEditGroupModel(Group group);

    GroupEditModel mapGroupEditModel(Group group);

    UserCreateModel.GroupModel mapUserCreateGroupModel(Group groupById);

    WareHouse mapWareHouse(WareHouseDTO dto);

    WareHouseDTO mapWareHouseDTO(WareHouse wareHouses);

    List<WareHouseDTO> mapWareHouseDTO(List<WareHouse> wareHouses);

    FactorySiteDTO mapFactorySiteDTO(FactorySite factorySiteById);

    List<FactorySiteDTO> mapFactorySiteDTO(List<FactorySite> factorySites);

    FactorySite mapFactorySite(FactorySiteDTO dto);

    DepartmentDTO mapDepartmentDTO(Department departmentById);

    List<DepartmentDTO> mapDepartmentDTO(List<Department> departments);

    Department mapDepartment(DepartmentDTO dto);
}

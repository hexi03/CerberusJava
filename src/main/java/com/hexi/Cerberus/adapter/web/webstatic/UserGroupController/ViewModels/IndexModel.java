package com.hexi.Cerberus.adapter.web.webstatic.UserGroupController.ViewModels;

import lombok.Data;

import java.util.List;

@Data
public class IndexModel {
    private final List<GroupDetailsModel> groups;
    private final List<UserDetailsModel> users;
}

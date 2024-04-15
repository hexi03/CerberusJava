package com.hexi.Cerberus.adapter.web.webstatic.UserGroupController.ViewModels;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class GroupCreateModel {
    @NotNull
    @Size(min = 3, max = 50)
    public String name = "";
}

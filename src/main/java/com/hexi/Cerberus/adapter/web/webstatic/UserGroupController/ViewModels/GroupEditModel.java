package com.hexi.Cerberus.adapter.web.webstatic.UserGroupController.ViewModels;

import com.hexi.Cerberus.domain.group.GroupID;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.UUID;

@Data
public class GroupEditModel {
    @NotNull
    @UUID
    public GroupID id;
    @NotNull
    @Size(min = 3,max = 50)
    public String name;
}

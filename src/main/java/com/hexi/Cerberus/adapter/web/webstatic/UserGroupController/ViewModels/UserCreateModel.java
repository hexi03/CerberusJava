package com.hexi.Cerberus.adapter.web.webstatic.UserGroupController.ViewModels;

import com.hexi.Cerberus.domain.commontypes.GroupID;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.List;

@Data
@Builder
public class UserCreateModel {
    @NotNull
    @Size(min = 3,max = 50)
    public String name = "";
    @NotNull
    @Email
    public String email = "";
    @NotNull
    @Size(min = 10,max = 50)
    public String passwordHash = "";

    @Singular
    public List<GroupID> groups;
    @Singular
    public List<GroupModel> groupModels;

    public static class GroupModel {
        public GroupID id;
        public String name;
    }

}

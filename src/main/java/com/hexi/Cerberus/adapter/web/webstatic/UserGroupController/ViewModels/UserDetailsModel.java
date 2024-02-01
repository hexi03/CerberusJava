package com.hexi.Cerberus.adapter.web.webstatic.UserGroupController.ViewModels;

import com.hexi.Cerberus.domain.commontypes.GroupID;
import com.hexi.Cerberus.domain.commontypes.UserID;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import org.hibernate.validator.constraints.UUID;

import java.util.List;

@Data
@Builder
public class UserDetailsModel {
    @NotNull
    @UUID
    public UserID id;
    @NotNull
    @Size(min = 3,max = 50)
    public String name;
    @NotNull
    @Email
    public String email;

    @Singular
    public List<GroupModel> groupModels;

    public static class GroupModel {
        public GroupID id;
        public String name;
    }
}

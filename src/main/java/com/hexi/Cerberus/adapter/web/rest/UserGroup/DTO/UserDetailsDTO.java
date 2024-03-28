package com.hexi.Cerberus.adapter.web.rest.UserGroup.DTO;

import com.hexi.Cerberus.domain.group.Group;
import com.hexi.Cerberus.domain.group.GroupID;
import com.hexi.Cerberus.domain.user.UserID;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserDetailsDTO {
    UserID id;
    String name;
    List<GroupID> groups;
}

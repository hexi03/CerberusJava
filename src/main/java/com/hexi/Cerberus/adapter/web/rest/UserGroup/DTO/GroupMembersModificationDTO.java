package com.hexi.Cerberus.adapter.web.rest.UserGroup.DTO;

import com.hexi.Cerberus.domain.group.GroupID;
import com.hexi.Cerberus.domain.user.UserID;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class GroupMembersModificationDTO {
    GroupID id;
    List<UserID> users;
    boolean exclude;
}

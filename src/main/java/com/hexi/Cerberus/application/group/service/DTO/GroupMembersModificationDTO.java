package com.hexi.Cerberus.application.group.service.DTO;

import com.hexi.Cerberus.domain.group.GroupID;
import com.hexi.Cerberus.domain.user.UserID;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class GroupMembersModificationDTO {
    GroupID id;
    List<UserID> users;
    boolean exclude;
}

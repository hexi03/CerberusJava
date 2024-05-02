package com.hexi.Cerberus.application.group.service.DTO;

import com.hexi.Cerberus.domain.group.GroupID;
import com.hexi.Cerberus.domain.user.UserID;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GroupDetailsDTO {
    GroupID id;
    String name;
    List<UserID> users;
}

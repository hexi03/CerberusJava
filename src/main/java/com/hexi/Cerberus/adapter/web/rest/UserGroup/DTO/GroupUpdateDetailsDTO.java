package com.hexi.Cerberus.adapter.web.rest.UserGroup.DTO;

import com.hexi.Cerberus.domain.group.GroupID;
import com.hexi.Cerberus.domain.user.UserID;
import lombok.Builder;
import lombok.Data;

@Data
public class GroupUpdateDetailsDTO {
    GroupID id;
    String name;
}
package com.hexi.Cerberus.application.group.service.DTO;

import com.hexi.Cerberus.domain.group.GroupID;
import com.hexi.Cerberus.domain.user.UserID;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GroupUpdateDetailsDTO {
    GroupID id;
    String name;
}
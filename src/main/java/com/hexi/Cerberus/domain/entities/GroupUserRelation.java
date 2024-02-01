package com.hexi.Cerberus.domain.entities;

import com.hexi.Cerberus.domain.commontypes.GroupID;
import com.hexi.Cerberus.domain.commontypes.UserGroupID;
import com.hexi.Cerberus.domain.commontypes.UserID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GroupUserRelation {
    UserGroupID id;
    UserID userId;
    GroupID groupId;
}

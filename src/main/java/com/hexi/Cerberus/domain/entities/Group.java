package com.hexi.Cerberus.domain.entities;

import com.hexi.Cerberus.domain.commontypes.GroupID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Group {
    GroupID id;
    String name;
}

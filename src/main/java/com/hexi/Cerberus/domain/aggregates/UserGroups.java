package com.hexi.Cerberus.domain.aggregates;

import com.hexi.Cerberus.domain.entities.Group;
import com.hexi.Cerberus.domain.entities.User;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.List;

@Data
@Builder
public class UserGroups {
    private final User user;
    @Singular
    private final List<Group> groups;
}

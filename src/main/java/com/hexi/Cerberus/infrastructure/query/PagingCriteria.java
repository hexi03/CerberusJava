package com.hexi.Cerberus.infrastructure.query;

import com.hexi.Cerberus.infrastructure.entity.EntityID;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class PagingCriteria {
    private EntityID key;
    private Integer count;
    private Direction direction;
    public PagingCriteria(EntityID key, Integer count, Direction direction) {
        this.key = key;
        this.count = count;
        this.direction = direction;
    }

    public PagingCriteria(EntityID key, Integer count) {
        this.key = key;
        this.count = count;
        this.direction = Direction.FORWARD;
    }

    public static enum Direction {
        FORWARD,
        BACKWARD
    }


}

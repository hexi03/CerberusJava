package com.hexi.Cerberus.infrastructure.query;


import lombok.Getter;
import lombok.experimental.SuperBuilder;
@Getter
@SuperBuilder
public abstract class SortCriteria {
    Boolean descending;
}

package com.hexi.Cerberus.infrastructure.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor

public class PagingCriteria {
    Integer shift;
    Integer count;
}

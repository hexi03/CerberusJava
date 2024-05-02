package com.hexi.Cerberus.infrastructure.query;

import com.hexi.Cerberus.infrastructure.entity.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Query {
    Class<? extends Entity> targetEntity;
    SortCriteria sortCriteria;
    PagingCriteria pagingCriteria;
    FilterCriteria filterCriteria;

    public Query(Class<? extends Entity> targetEntity, FilterCriteria filterCriteria, SortCriteria sortCriteria, PagingCriteria pagingCriteria) {
        this.targetEntity = targetEntity;
        this.filterCriteria = filterCriteria;
        this.sortCriteria = sortCriteria;
        this.pagingCriteria = pagingCriteria;
    }
}

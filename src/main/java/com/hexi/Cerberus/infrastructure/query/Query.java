package com.hexi.Cerberus.infrastructure.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Query {
    SortCriteria sortCriteria;
    PagingCriteria pagingCriteria;
    FilterCriteria filterCriteria;

    public Query(FilterCriteria filterCriteria, SortCriteria sortCriteria, PagingCriteria pagingCriteria) {

    }
}

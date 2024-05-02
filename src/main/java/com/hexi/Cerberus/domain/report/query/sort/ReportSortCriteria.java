package com.hexi.Cerberus.domain.report.query.sort;

import com.hexi.Cerberus.infrastructure.query.SortCriteria;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
public class ReportSortCriteria extends SortCriteria {
    public final List<SortBy> sortBy;
    public final List<SortType> sortType;

    public enum SortBy {
        CREATED,
        DELETED
    }
    public enum SortType {
        ASCENDING,
        DESCENDING
    }
}

package com.hexi.Cerberus.domain.report.query.sort;

import com.hexi.Cerberus.infrastructure.query.SortCriteria;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ReportSortCriteria extends SortCriteria {
    public final SortBy sortBy;
    public final SortType sortType;

    public enum SortBy {
        CREATED,
        DELETED
    }
    public enum SortType {
        ASCENDING,
        DESCENDING
    }
}

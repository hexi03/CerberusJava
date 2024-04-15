package com.hexi.Cerberus.domain.report.query.filter;

import jakarta.persistence.Embeddable;

@Embeddable
public enum ReportStatus {
    ACTIVE,
    //    ARCHIVED,
    DELETED
}

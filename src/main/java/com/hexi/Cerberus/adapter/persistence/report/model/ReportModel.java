package com.hexi.Cerberus.adapter.persistence.report.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "report_registry")

@Getter
@Setter
public class ReportModel {
    @Deprecated
    protected ReportModel() {

    }
}

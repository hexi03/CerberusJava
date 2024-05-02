package com.hexi.Cerberus.adapter.persistence.report.repository;

import com.hexi.Cerberus.adapter.persistence.report.base.ReportModel;
import com.hexi.Cerberus.domain.report.Report;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.domain.report.factorysite.FactorySiteReport;
import com.hexi.Cerberus.domain.report.factorysite.SupplyRequirementReport;
import com.hexi.Cerberus.domain.report.factorysite.WorkShiftReport;
import com.hexi.Cerberus.domain.report.repository.ReportRepository;
import com.hexi.Cerberus.domain.report.warehouse.*;
import com.hexi.Cerberus.infrastructure.query.Query;
import jdk.jshell.spi.ExecutionControl;
import lombok.SneakyThrows;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface JpaReportRepository<T extends ReportModel, ID extends ReportID> extends ReportRepository<T, ID>, JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
    @SneakyThrows
    default List<T> findAllWithQuery(Query domainQuery){

        if (domainQuery.getPagingCriteria() != null)
            return findAll((Specification<T>)ReportSpecificationBuilder.getSpec(domainQuery), Pageable.ofSize(domainQuery.getPagingCriteria().getCount())).toList();
        else
            return findAll((Specification<T>)ReportSpecificationBuilder.getSpec(domainQuery));


    }



}

package com.hexi.Cerberus.adapter.persistence.report.repository;

import com.hexi.Cerberus.adapter.persistence.item.base.ItemEntry;
import com.hexi.Cerberus.adapter.persistence.report.base.ReportModel;
import com.hexi.Cerberus.domain.factorysite.FactorySite;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.domain.report.factorysite.WorkShiftReport;
import com.hexi.Cerberus.domain.report.repository.ReportRepository;
import com.hexi.Cerberus.domain.warehouse.WareHouse;
import com.hexi.Cerberus.domain.warehouse.WareHouseID;
import com.hexi.Cerberus.infrastructure.query.Query;
import lombok.SneakyThrows;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.stream.Collectors;

public interface JpaReportRepository<T extends ReportModel, ID extends ReportID> extends ReportRepository<T, ID>, JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
    @SneakyThrows
    default List<T> findAllWithQuery(Query domainQuery){
        System.out.println("HEXIQUERY");
        System.out.println(domainQuery.toString());
        if (domainQuery.getPagingCriteria() != null && domainQuery.getPagingCriteria().getCount() != null)
            return findAll((Specification<T>) ReportSpecificationFactory.getSpec(domainQuery), Pageable.ofSize(domainQuery.getPagingCriteria().getCount())).toList();
        else
            return findAll((Specification<T>) ReportSpecificationFactory.getSpec(domainQuery));


    }





}

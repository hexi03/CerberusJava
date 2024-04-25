package com.hexi.Cerberus.adapter.persistence.department.repository;

import com.hexi.Cerberus.adapter.persistence.department.base.DepartmentModel;
import com.hexi.Cerberus.domain.department.DepartmentID;
import com.hexi.Cerberus.domain.department.query.DepartmentFilterCriteria;
import com.hexi.Cerberus.domain.department.repository.DepartmentRepository;
import com.hexi.Cerberus.infrastructure.query.Query;
import jdk.jshell.spi.ExecutionControl;
import lombok.SneakyThrows;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface JpaDepartmentRepository<T extends DepartmentModel, ID extends DepartmentID>
        extends
        DepartmentRepository<T, ID>,
        JpaRepository<T, ID>,
        JpaSpecificationExecutor<DepartmentModel>
{

    @SneakyThrows
    default List<T> findAllWithQuery(Query domainQuery){
        throw new ExecutionControl.NotImplementedException(" ");
    }

}

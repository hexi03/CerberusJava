package com.hexi.Cerberus.adapter.persistence.department.repository;

import com.hexi.Cerberus.adapter.persistence.department.base.DepartmentModel;
import com.hexi.Cerberus.domain.department.DepartmentID;
import com.hexi.Cerberus.domain.department.query.DepartmentFilterCriteria;
import com.hexi.Cerberus.domain.department.repository.DepartmentRepository;
import com.hexi.Cerberus.infrastructure.query.Query;
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

    default List<T> findAllWithQuery(Query domainQuery){
        return findAll((Specification<DepartmentModel>)(root, query, builder) -> {
            DepartmentFilterCriteria fc = (DepartmentFilterCriteria) domainQuery.getFilterCriteria();
            

            return
        });
    }

}

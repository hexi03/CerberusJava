package com.hexi.Cerberus.adapter.persistence.department.repository;

import com.hexi.Cerberus.adapter.persistence.department.base.DepartmentModel;
import com.hexi.Cerberus.domain.department.DepartmentID;
import com.hexi.Cerberus.domain.department.repository.DepartmentRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaDepartmentRepository<T extends DepartmentModel, ID extends DepartmentID>
        extends
        DepartmentRepository<T, ID>,
        JpaRepository<T, ID> {

}

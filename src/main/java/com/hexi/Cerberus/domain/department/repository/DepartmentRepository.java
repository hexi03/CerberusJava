package com.hexi.Cerberus.domain.department.repository;

import com.hexi.Cerberus.domain.department.Department;
import com.hexi.Cerberus.domain.department.DepartmentID;
import com.hexi.Cerberus.infrastructure.repository.Repository;

public interface DepartmentRepository<T extends Department, ID extends DepartmentID> extends Repository<T, ID> {

}

package com.hexi.Cerberus.domain.services;

import com.hexi.Cerberus.domain.commontypes.DepartmentID;
import com.hexi.Cerberus.domain.entities.Department;

import java.util.List;

public interface DepartmentService {
    List<Department> getDepartments();
    Department getDepartmentById(DepartmentID id);
    void createDepartment(Department department);
    void updateDepartment(Department department);
    void deleteDepartment(DepartmentID id);
}

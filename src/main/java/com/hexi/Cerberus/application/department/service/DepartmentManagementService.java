package com.hexi.Cerberus.application.department.service;

import com.hexi.Cerberus.domain.department.Department;
import com.hexi.Cerberus.domain.department.DepartmentID;
import com.hexi.Cerberus.domain.department.command.CreateDepartmentCmd;
import com.hexi.Cerberus.domain.department.command.UpdateDepartmentDetailsCmd;
import com.hexi.Cerberus.infrastructure.query.Query;

import java.util.List;
import java.util.Optional;

public interface DepartmentManagementService {


    Optional<Department> displayBy(DepartmentID id);

    List<Department> displayAllBy(Query query);

    List<Department> displayAllBy();

    Department create(CreateDepartmentCmd cmd);

    void updateDetails(UpdateDepartmentDetailsCmd cmd);

    void delete(DepartmentID id);

}

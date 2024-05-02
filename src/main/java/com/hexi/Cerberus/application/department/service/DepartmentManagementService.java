package com.hexi.Cerberus.application.department.service;

import com.hexi.Cerberus.application.department.service.DTO.DepartmentDetailsDTO;
import com.hexi.Cerberus.domain.department.DepartmentID;
import com.hexi.Cerberus.domain.department.command.CreateDepartmentCmd;
import com.hexi.Cerberus.domain.department.command.UpdateDepartmentDetailsCmd;
import com.hexi.Cerberus.infrastructure.query.Query;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface DepartmentManagementService {


    Optional<DepartmentDetailsDTO> displayBy(DepartmentID id);

    List<DepartmentDetailsDTO> displayAllBy(Query query);

    List<DepartmentDetailsDTO> displayAll();

    DepartmentDetailsDTO create(CreateDepartmentCmd cmd);

    void updateDetails(UpdateDepartmentDetailsCmd cmd);

    void delete(DepartmentID id);

}

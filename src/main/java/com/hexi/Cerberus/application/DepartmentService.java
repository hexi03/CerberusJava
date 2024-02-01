package com.hexi.Cerberus.application;

import com.hexi.Cerberus.domain.commontypes.DepartmentID;
import com.hexi.Cerberus.application.DTO.DepartmentDTO;

import java.util.List;

public interface DepartmentService {
    DepartmentDTO getDepartmentById(DepartmentID id);
    List<DepartmentDTO> getAllDepartments();
    void createDepartment(DepartmentDTO dto);
    void updateDepartment(DepartmentDTO dto);
    void deleteDepartment(DepartmentID id);

}

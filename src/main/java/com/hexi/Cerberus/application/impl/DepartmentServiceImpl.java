package com.hexi.Cerberus.application.impl;

import com.hexi.Cerberus.domain.commontypes.DepartmentID;
import com.hexi.Cerberus.application.DTO.DepartmentDTO;
import com.hexi.Cerberus.application.Mapper;
import com.hexi.Cerberus.application.DepartmentService;

import java.util.List;

public class DepartmentServiceImpl implements DepartmentService {

    private com.hexi.Cerberus.domain.services.DepartmentService departmentService;

    // Добавьте мапперы
    private Mapper mapper;

    public DepartmentServiceImpl(
            com.hexi.Cerberus.domain.services.DepartmentService departmentService,
            Mapper mapper
    ) {
        this.departmentService = departmentService;
        this.mapper = mapper;
    }
    @Override
    public DepartmentDTO getDepartmentById(DepartmentID id) {
        return mapper.mapDepartmentDTO(departmentService.getDepartmentById(id));
    }

    @Override
    public List<DepartmentDTO> getAllDepartments() {
        return mapper.mapDepartmentDTO(departmentService.getDepartments());
    }

    @Override
    public void createDepartment(DepartmentDTO dto) {
        departmentService.createDepartment(mapper.mapDepartment(dto));
    }

    @Override
    public void updateDepartment(DepartmentDTO dto) {
        departmentService.updateDepartment(mapper.mapDepartment(dto));
    }

    @Override
    public void deleteDepartment(DepartmentID id) {
        departmentService.deleteDepartment(id);
    }
}


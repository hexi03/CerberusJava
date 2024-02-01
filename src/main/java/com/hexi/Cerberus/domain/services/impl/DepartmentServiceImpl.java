package com.hexi.Cerberus.domain.services.impl;

import com.hexi.Cerberus.domain.commontypes.DepartmentID;
import com.hexi.Cerberus.domain.exceptions.DepartmentNotFoundException;
import com.hexi.Cerberus.domain.entities.Department;
import com.hexi.Cerberus.domain.repository.DepartmentRepository;
import com.hexi.Cerberus.domain.services.DepartmentService;

import java.util.List;
import java.util.UUID;

public class DepartmentServiceImpl implements DepartmentService {

    private DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }
    @Override
    public List<Department> getDepartments() {
        return departmentRepository.getAll();
    }

    @Override
    public Department getDepartmentById(DepartmentID id) {
        if (departmentRepository.isExists(id.getId()))
            return departmentRepository.getById(id.getId());
        else
            throw new DepartmentNotFoundException();
    }

    @Override
    public void createDepartment(Department department) {
        department.setId(new DepartmentID(UUID.randomUUID().toString()));
        departmentRepository.append(department);
    }

    @Override
    public void updateDepartment(Department department) {
        if (departmentRepository.isExists(department.getId().getId()))
            departmentRepository.update(department);
        else
            throw new DepartmentNotFoundException();
    }

    @Override
    public void deleteDepartment(DepartmentID id) {
        if (departmentRepository.isExists(id.getId()))
        departmentRepository.deleteById(id.getId());
        else
            throw new DepartmentNotFoundException();
    }
}

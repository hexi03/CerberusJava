package com.hexi.Cerberus.adapter.persistence.department.impl;

import com.hexi.Cerberus.domain.department.Department;
import com.hexi.Cerberus.domain.department.DepartmentID;
import com.hexi.Cerberus.domain.department.repository.DepartmentRepository;
import com.hexi.Cerberus.infrastructure.query.Query;

import java.util.List;
import java.util.Optional;

public class DepartmentRepositoryImpl implements DepartmentRepository {
    @Override
    public Optional<Department> displayById(DepartmentID id) {
        return Optional.empty();
    }

    @Override
    public List<Department> displayById(List<DepartmentID> departmentIDS) {
        return null;
    }

    @Override
    public List<Department> displayAll(Query query) {
        return null;
    }

    @Override
    public List<Department> displayAll() {
        return null;
    }

    @Override
    public Department append(Department user) {
        return null;
    }

    @Override
    public void update(Department user) {

    }

    @Override
    public void deleteById(DepartmentID id) {

    }

    @Override
    public boolean isExists(DepartmentID id) {
        return false;
    }
}

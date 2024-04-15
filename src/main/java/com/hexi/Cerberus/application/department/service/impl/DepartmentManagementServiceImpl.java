package com.hexi.Cerberus.application.department.service.impl;

import com.hexi.Cerberus.application.department.service.DepartmentManagementService;
import com.hexi.Cerberus.domain.department.Department;
import com.hexi.Cerberus.domain.department.DepartmentFactory;
import com.hexi.Cerberus.domain.department.DepartmentID;
import com.hexi.Cerberus.domain.department.DepartmentUpdater;
import com.hexi.Cerberus.domain.department.command.CreateDepartmentCmd;
import com.hexi.Cerberus.domain.department.command.UpdateDepartmentDetailsCmd;
import com.hexi.Cerberus.domain.department.repository.DepartmentRepository;
import com.hexi.Cerberus.infrastructure.messaging.MessagePublisher;
import com.hexi.Cerberus.infrastructure.query.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.security.acls.model.MutableAclService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class DepartmentManagementServiceImpl implements DepartmentManagementService {
    public final DepartmentRepository departmentRepository;
    public final MutableAclService aclService;
    public final MessagePublisher messagePublisher;
    public final DepartmentFactory departmentFactory;
    public final DepartmentUpdater departmentUpdater;

    @Override
    public Optional<Department> displayBy(DepartmentID id) {
        return departmentRepository.findById(id);
    }

    @Override
    public List<Department> displayAllBy(Query query) {
        return departmentRepository.findAll(query);
    }

    @Override
    public List<Department> displayAllBy() {
        return departmentRepository.findAll();

    }

    @Override
    public Department create(CreateDepartmentCmd cmd) {
        cmd.validate().onFailedThrow();
        Department department = departmentFactory.from(cmd);

        departmentRepository.append(department);
        department.initAcl(aclService);
        messagePublisher.publish(department.edjectEvents());
        return department;

    }

    @Override
    public void updateDetails(UpdateDepartmentDetailsCmd cmd) {
        cmd.validate().onFailedThrow();
        Optional<Department> department = departmentRepository.findById(cmd.getDepartmentId());
        department.orElseThrow(() -> new RuntimeException(String.format("There are no department with id %s", cmd.getDepartmentId().toString())));
        departmentUpdater.updateBy(department.get(), cmd);
        departmentRepository.update(department.get());
        messagePublisher.publish(department.get().edjectEvents());
    }

    @Override
    public void delete(DepartmentID id) {
        departmentRepository.deleteById(id);
    }
}

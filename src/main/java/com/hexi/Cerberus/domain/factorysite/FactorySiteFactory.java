package com.hexi.Cerberus.domain.factorysite;

import com.hexi.Cerberus.domain.department.Department;
import com.hexi.Cerberus.domain.department.repository.DepartmentRepository;
import com.hexi.Cerberus.domain.factorysite.command.CreateFactorySiteCmd;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class FactorySiteFactory {
    public final DepartmentRepository departmentRepository;
    public FactorySite from(CreateFactorySiteCmd cmd) {
        Optional<Department> department = departmentRepository.displayById(cmd.getTargetDepartmentId());
        department.orElseThrow(() -> new RuntimeException(String.format("There is no Department with id: %s", cmd.getTargetDepartmentId().toString())));
        return new FactorySite(
                new FactorySiteID(),
                department.get(),
                cmd.getName()
        );
    }
}

package com.hexi.Cerberus.adapter.persistence.factorysite.factory;

import com.hexi.Cerberus.adapter.persistence.factorysite.base.FactorySiteModel;
import com.hexi.Cerberus.domain.department.Department;
import com.hexi.Cerberus.domain.department.repository.DepartmentRepository;
import com.hexi.Cerberus.domain.factorysite.FactorySite;
import com.hexi.Cerberus.domain.factorysite.FactorySiteFactory;
import com.hexi.Cerberus.domain.factorysite.FactorySiteID;
import com.hexi.Cerberus.domain.factorysite.command.CreateFactorySiteCmd;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class JpaFactorySiteFactoryImpl implements FactorySiteFactory {
    public final DepartmentRepository departmentRepository;

    public FactorySite from(CreateFactorySiteCmd cmd) {
        Optional<Department> department = departmentRepository.findById(cmd.getTargetDepartmentId());
        department.orElseThrow(() -> new RuntimeException(String.format("There is no Department with id: %s", cmd.getTargetDepartmentId().toString())));
        return new FactorySiteModel(
                new FactorySiteID(),
                department.get(),
                cmd.getName()
        );
    }
}

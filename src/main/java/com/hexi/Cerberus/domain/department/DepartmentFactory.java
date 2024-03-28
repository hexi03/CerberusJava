package com.hexi.Cerberus.domain.department;

import com.hexi.Cerberus.domain.department.command.CreateDepartmentCmd;
import com.hexi.Cerberus.domain.factorysite.FactorySite;
import com.hexi.Cerberus.domain.factorysite.repository.FactorySiteRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DepartmentFactory {


    public Department from(CreateDepartmentCmd cmd) {
        return new Department(new DepartmentID(), cmd.getName());
    }
}

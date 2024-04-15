package com.hexi.Cerberus.adapter.persistence.department.factory;

import com.hexi.Cerberus.adapter.persistence.department.base.DepartmentModel;
import com.hexi.Cerberus.domain.department.Department;
import com.hexi.Cerberus.domain.department.DepartmentFactory;
import com.hexi.Cerberus.domain.department.DepartmentID;
import com.hexi.Cerberus.domain.department.command.CreateDepartmentCmd;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JpaDepartmentFactoryImpl implements DepartmentFactory {


    public Department from(CreateDepartmentCmd cmd) {
        return new DepartmentModel(new DepartmentID(), cmd.getName());
    }
}

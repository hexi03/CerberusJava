package com.hexi.Cerberus.domain.department;

import com.hexi.Cerberus.domain.department.Department;
import com.hexi.Cerberus.domain.department.DepartmentID;
import com.hexi.Cerberus.adapter.persistence.department.base.DepartmentModel;
import com.hexi.Cerberus.domain.department.command.CreateDepartmentCmd;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public interface DepartmentFactory {
    Department from(CreateDepartmentCmd cmd);
}

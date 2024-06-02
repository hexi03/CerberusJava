package com.hexi.Cerberus.domain.department;

import com.hexi.Cerberus.application.department.service.command.CreateDepartmentCmd;
import org.springframework.stereotype.Component;

@Component
public interface DepartmentFactory {
    Department from(CreateDepartmentCmd cmd);
}

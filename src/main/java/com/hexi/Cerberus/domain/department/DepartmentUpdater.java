package com.hexi.Cerberus.domain.department;

import com.hexi.Cerberus.domain.department.command.UpdateDepartmentDetailsCmd;
import org.springframework.stereotype.Component;

@Component
public class DepartmentUpdater {
    public void updateBy(Department department, UpdateDepartmentDetailsCmd cmd) {
        department.setName(cmd.getName());
    }
}

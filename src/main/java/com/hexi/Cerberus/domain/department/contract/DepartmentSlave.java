package com.hexi.Cerberus.domain.department.contract;

import com.hexi.Cerberus.domain.department.Department;

public interface DepartmentSlave {
    void registerParentDepartment(Department d);

    void resetParentDepartment();
}

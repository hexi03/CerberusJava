package com.hexi.Cerberus.domain.service;

import com.hexi.Cerberus.domain.department.Department;
import com.hexi.Cerberus.domain.factorysite.FactorySite;
import com.hexi.Cerberus.domain.warehouse.WareHouse;
import org.springframework.stereotype.Service;

@Service
public interface DepartmentManagementService {
    void addFactorySiteToDepartment(Department department, FactorySite factorySite);

    void addWareHouseToDepartment(Department department, WareHouse wareHouse);

}

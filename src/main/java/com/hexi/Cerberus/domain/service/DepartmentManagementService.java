package com.hexi.Cerberus.domain.service;

import com.hexi.Cerberus.domain.department.Department;
import com.hexi.Cerberus.domain.factorysite.FactorySite;
import com.hexi.Cerberus.domain.warehouse.WareHouse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Transactional
public interface DepartmentManagementService {
    void addFactorySiteToDepartment(Department department, FactorySite factorySite);

    void addWareHouseToDepartment(Department department, WareHouse wareHouse);

}

package com.hexi.Cerberus.domain.service.impl;

import com.hexi.Cerberus.domain.department.Department;
import com.hexi.Cerberus.domain.department.repository.DepartmentRepository;
import com.hexi.Cerberus.domain.factorysite.FactorySite;
import com.hexi.Cerberus.domain.factorysite.repository.FactorySiteRepository;
import com.hexi.Cerberus.domain.service.DepartmentManagementService;
import com.hexi.Cerberus.domain.warehouse.WareHouse;
import com.hexi.Cerberus.domain.warehouse.repository.WareHouseRepository;
import com.hexi.Cerberus.infrastructure.messaging.MessagePublisher;
import lombok.AllArgsConstructor;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class DepartmentManagementServiceImpl implements DepartmentManagementService {
    private final MutableAclService aclService;
    private final DepartmentRepository departmentRepository;
    private final FactorySiteRepository factorySiteRepository;
    private final WareHouseRepository wareHouseRepository;
    private final MessagePublisher messagePublisher;

    @Override
    public void addFactorySiteToDepartment(Department department, FactorySite factorySite) {
        department.registerFactorySite(factorySite);
        factorySite.setParent(aclService, factorySite.getObjectIdentity());
        departmentRepository.save(department);
        factorySiteRepository.save(factorySite);
        messagePublisher.publish(department.edjectEvents());
    }

    @Override
    public void addWareHouseToDepartment(Department department, WareHouse wareHouse) {
        department.registerWareHouse(wareHouse);
        wareHouse.setParent(aclService, wareHouse.getObjectIdentity());
        departmentRepository.save(department);
        wareHouseRepository.save(wareHouse);
        messagePublisher.publish(department.edjectEvents());
    }
}

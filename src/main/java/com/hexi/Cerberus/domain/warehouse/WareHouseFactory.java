package com.hexi.Cerberus.domain.warehouse;

import com.hexi.Cerberus.domain.department.Department;
import com.hexi.Cerberus.domain.department.repository.DepartmentRepository;
import com.hexi.Cerberus.domain.warehouse.command.CreateWareHouseCmd;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class WareHouseFactory {
    public final DepartmentRepository departmentRepository;
    public WareHouse from(CreateWareHouseCmd cmd) {
        Optional<Department> department = departmentRepository.displayById(cmd.getTargetDepartmentId());
        department.orElseThrow(() -> new RuntimeException(String.format("There is no Department with id: %s", cmd.getTargetDepartmentId().toString())));

        return new WareHouse(new WareHouseID(), department.get(), cmd.getName());
    }
}

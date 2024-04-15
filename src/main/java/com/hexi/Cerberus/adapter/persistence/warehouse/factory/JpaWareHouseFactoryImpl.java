package com.hexi.Cerberus.adapter.persistence.warehouse.factory;

import com.hexi.Cerberus.adapter.persistence.department.base.DepartmentModel;
import com.hexi.Cerberus.adapter.persistence.warehouse.base.WareHouseModel;
import com.hexi.Cerberus.domain.department.repository.DepartmentRepository;
import com.hexi.Cerberus.domain.warehouse.WareHouse;
import com.hexi.Cerberus.domain.warehouse.WareHouseFactory;
import com.hexi.Cerberus.domain.warehouse.WareHouseID;
import com.hexi.Cerberus.domain.warehouse.command.CreateWareHouseCmd;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class JpaWareHouseFactoryImpl implements WareHouseFactory {
    public final DepartmentRepository departmentRepository;

    public WareHouse from(CreateWareHouseCmd cmd) {
        Optional<DepartmentModel> department = departmentRepository.findById(cmd.getTargetDepartmentId());
        department.orElseThrow(() -> new RuntimeException(String.format("There is no Department with id: %s", cmd.getTargetDepartmentId().toString())));

        return new WareHouseModel(new WareHouseID(), department.get(), cmd.getName());
    }
}

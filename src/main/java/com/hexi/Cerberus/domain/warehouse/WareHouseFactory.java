package com.hexi.Cerberus.domain.warehouse;

import com.hexi.Cerberus.adapter.persistence.department.base.DepartmentModel;
import com.hexi.Cerberus.domain.department.repository.DepartmentRepository;
import com.hexi.Cerberus.domain.warehouse.WareHouse;
import com.hexi.Cerberus.domain.warehouse.WareHouseID;
import com.hexi.Cerberus.adapter.persistence.warehouse.base.WareHouseModel;
import com.hexi.Cerberus.domain.warehouse.command.CreateWareHouseCmd;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface WareHouseFactory {
    WareHouse from(CreateWareHouseCmd cmd);
}

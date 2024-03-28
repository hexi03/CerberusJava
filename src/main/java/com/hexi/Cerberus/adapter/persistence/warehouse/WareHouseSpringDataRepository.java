package com.hexi.Cerberus.adapter.persistence.warehouse;

import com.hexi.Cerberus.adapter.persistence.warehouse.model.WareHouseModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WareHouseSpringDataRepository extends JpaRepository<WareHouseModel, UUID> {
}

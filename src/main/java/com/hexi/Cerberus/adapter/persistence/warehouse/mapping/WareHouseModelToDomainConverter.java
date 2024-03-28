package com.hexi.Cerberus.adapter.persistence.warehouse.mapping;

import com.hexi.Cerberus.adapter.persistence.warehouse.model.WareHouseModel;
import com.hexi.Cerberus.domain.warehouse.WareHouse;

import java.util.function.Function;

public class WareHouseModelToDomainConverter implements Function<WareHouseModel, WareHouse> {

    @Override
    public WareHouse apply(WareHouseModel model) {
        return new WareHouse(model.getId(), model.getName(), model.);
    }
}

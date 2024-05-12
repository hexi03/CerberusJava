package com.hexi.Cerberus.domain.report.factorysite;

import com.hexi.Cerberus.application.report.service.DTO.details.ReportDetails;
import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.product.Product;
import com.hexi.Cerberus.domain.warehouse.WareHouse;

import java.util.List;
import java.util.Map;

public interface WorkShiftReport extends FactorySiteReport {
    void setTargetWareHouses(List<WareHouse> wareHouse);

    Map<Product, Integer> getProduced();

    void setProduced(Map<Product, Integer> producedMap);

    Map<Item, Integer> getRemains();

    void setRemains(Map<Item, Integer> remainsMap);

    Map<Item, Integer> getUnclaimedRemains();

    void setUnclaimedRemains(Map<Item, Integer> remainsMap);

    Map<Item, Integer> getLosses();

    void setLosses(Map<Item, Integer> lossesMap);

    List<WareHouse> getTargetWareHouses();
}

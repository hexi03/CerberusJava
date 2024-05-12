package com.hexi.Cerberus.domain.item.service;

import com.hexi.Cerberus.domain.factorysite.FactorySite;
import com.hexi.Cerberus.domain.item.ItemID;

import java.util.Map;

public interface ItemRegistriesQueryService {
    Map<ItemID, Integer> getFactorySiteLostedOnSiteConsumables(FactorySite factorySite);
}

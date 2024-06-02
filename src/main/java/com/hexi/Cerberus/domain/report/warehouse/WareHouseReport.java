package com.hexi.Cerberus.domain.report.warehouse;

import com.hexi.Cerberus.domain.report.Report;
import com.hexi.Cerberus.domain.warehouse.WareHouse;

public interface WareHouseReport extends Report {
    WareHouse getWareHouse();
}

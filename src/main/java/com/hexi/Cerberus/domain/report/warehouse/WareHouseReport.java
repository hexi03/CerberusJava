package com.hexi.Cerberus.domain.report.warehouse;

import com.hexi.Cerberus.domain.report.Report;
import com.hexi.Cerberus.domain.warehouse.WareHouseID;

public interface WareHouseReport extends Report {
    WareHouseID getWareHouseId();
}

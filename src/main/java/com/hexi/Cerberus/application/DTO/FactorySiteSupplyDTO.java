package com.hexi.Cerberus.application.DTO;

import com.hexi.Cerberus.domain.commontypes.WareHouseID;
import lombok.Data;

import java.util.List;
@Data
public class FactorySiteSupplyDTO {
    List<WareHouseID> suppliers;
}

package com.hexi.Cerberus.application.warehouse.service.DTO;

import com.hexi.Cerberus.domain.warehouse.WareHouseID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class WareHouseUpdateDetailsDTO {
    WareHouseID id;
    String name;
}

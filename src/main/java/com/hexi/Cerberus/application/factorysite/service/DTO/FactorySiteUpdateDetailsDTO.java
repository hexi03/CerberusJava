package com.hexi.Cerberus.application.factorysite.service.DTO;

import com.hexi.Cerberus.domain.factorysite.FactorySiteID;
import lombok.Data;


@Data
public class FactorySiteUpdateDetailsDTO {
    FactorySiteID id;
    String name;
}

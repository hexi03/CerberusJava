package com.hexi.Cerberus.application.warehouse.service.DTO;

import com.hexi.Cerberus.application.mapper.DTO.StateProblemDTO;
import com.hexi.Cerberus.application.mapper.DTO.StateWarningDTO;
import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.infrastructure.StateProblem;
import com.hexi.Cerberus.infrastructure.StateWarning;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.List;
import java.util.Map;

@Builder
@Data
public class WareHouseStateDTO {
    @Singular
    List<StateProblemDTO> problems;
    @Singular
    List<StateWarningDTO> warnings;

    Map<ItemID, Integer> items;
}

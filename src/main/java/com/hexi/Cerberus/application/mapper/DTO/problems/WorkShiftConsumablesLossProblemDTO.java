package com.hexi.Cerberus.application.mapper.DTO.problems;

import com.hexi.Cerberus.application.mapper.DTO.StateProblemDTO;
import com.hexi.Cerberus.domain.item.ItemID;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
@Getter
@Setter
public class WorkShiftConsumablesLossProblemDTO extends StateProblemDTO {
    private static final String WorkShiftConsumablesLossProblem = "WorkShiftConsumablesLossProblem";
    Map<ItemID, Integer> lostedOnSiteConsumables;


    public WorkShiftConsumablesLossProblemDTO(Map<ItemID, Integer> lostedOnSiteConsumables) {
        super(WorkShiftConsumablesLossProblem);
        this.lostedOnSiteConsumables = lostedOnSiteConsumables;

    }

    public WorkShiftConsumablesLossProblemDTO() {
        super(WorkShiftConsumablesLossProblem);

    }
}

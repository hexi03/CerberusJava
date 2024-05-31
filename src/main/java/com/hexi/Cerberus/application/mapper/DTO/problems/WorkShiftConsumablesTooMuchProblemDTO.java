package com.hexi.Cerberus.application.mapper.DTO.problems;

import com.hexi.Cerberus.application.mapper.DTO.StateProblemDTO;
import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.infrastructure.StateProblem;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
@Getter
@Setter
public class WorkShiftConsumablesTooMuchProblemDTO extends StateProblemDTO {
    private static final String WorkShiftConsumablesTooMuchProblem = "WorkShiftConsumablesTooMuchProblem";
    Map<ItemID, Integer> lostedOnSiteConsumablesNeg;


    public WorkShiftConsumablesTooMuchProblemDTO(Map<ItemID, Integer> lostedOnSiteConsumablesNeg) {
        super(WorkShiftConsumablesTooMuchProblem);
        this.lostedOnSiteConsumablesNeg = lostedOnSiteConsumablesNeg;
    }
    public WorkShiftConsumablesTooMuchProblemDTO() {
        super(WorkShiftConsumablesTooMuchProblem);

    }
}

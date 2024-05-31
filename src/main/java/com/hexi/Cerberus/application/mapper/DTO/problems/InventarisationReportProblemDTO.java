package com.hexi.Cerberus.application.mapper.DTO.problems;

import com.hexi.Cerberus.application.mapper.DTO.StateProblemDTO;
import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.report.ReportID;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
@Getter
@Setter
public class InventarisationReportProblemDTO extends StateProblemDTO {
    private static final String InventarisationReportProblem = "InventarisationReportProblem";
    ReportID rep;
    Map<ItemID, Integer> differrence;
    public InventarisationReportProblemDTO(ReportID id, Map<ItemID, Integer> differrence) {
        super(InventarisationReportProblem);
        this.differrence = differrence;
        rep = id;
    }


    public InventarisationReportProblemDTO() {
        super(InventarisationReportProblem);
    }

   }

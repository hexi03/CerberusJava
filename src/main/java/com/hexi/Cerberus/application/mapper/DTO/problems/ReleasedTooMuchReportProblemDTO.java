package com.hexi.Cerberus.application.mapper.DTO.problems;

import com.hexi.Cerberus.application.mapper.DTO.StateProblemDTO;
import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.infrastructure.StateProblem;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
@Getter
@Setter
public class ReleasedTooMuchReportProblemDTO extends StateProblemDTO {
    private static final String ReleasedTooMuchReportProblem = "ReleasedTooMuchReportProblem";
    ReportID rep;
    Map<ItemID, Integer> differrence;

    public ReleasedTooMuchReportProblemDTO(ReportID rep, Map<ItemID, Integer> differrence) {
        super(ReleasedTooMuchReportProblem);
        this.rep = rep;
        this.differrence = differrence;
    }

    public ReleasedTooMuchReportProblemDTO() {
        super(ReleasedTooMuchReportProblem);
    }
}

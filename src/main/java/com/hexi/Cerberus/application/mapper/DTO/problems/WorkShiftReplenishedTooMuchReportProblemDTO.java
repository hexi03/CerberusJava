package com.hexi.Cerberus.application.mapper.DTO.problems;

import com.hexi.Cerberus.application.mapper.DTO.StateProblemDTO;
import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.report.ReportID;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
@Getter
@Setter
public class WorkShiftReplenishedTooMuchReportProblemDTO extends StateProblemDTO {


    private static final String WorkShiftReplenishedTooMuchReportProblem = "WorkShiftReplenishedTooMuchReportProblem";

    public enum By{
        Produced,
        UnclaimedRemains
    }
    ReportID rep; By by;
    Map<ItemID, Integer> differrence;

    public WorkShiftReplenishedTooMuchReportProblemDTO(ReportID rep, By by, Map<ItemID, Integer> differrence) {
        super(WorkShiftReplenishedTooMuchReportProblem);
        this.rep = rep;
        this.by = by;
        this.differrence = differrence;
    }

    public WorkShiftReplenishedTooMuchReportProblemDTO() {
        super(WorkShiftReplenishedTooMuchReportProblem);
    }
}

package com.hexi.Cerberus.domain.report.command.update;

import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.infrastructure.command.Command;
import com.hexi.Cerberus.infrastructure.command.CommandId;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public abstract class UpdateReportCmd  implements Command {
    CommandId id;
    ReportID reportID;
    @Override
    public CommandId getId() {
        return id;
    }

}

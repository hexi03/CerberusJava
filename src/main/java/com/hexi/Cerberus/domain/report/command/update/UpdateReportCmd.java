package com.hexi.Cerberus.domain.report.command.update;

import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.domain.user.UserID;
import com.hexi.Cerberus.infrastructure.ValidationResult;
import com.hexi.Cerberus.infrastructure.command.Command;
import com.hexi.Cerberus.infrastructure.command.CommandId;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;

@Getter
@SuperBuilder
public abstract class UpdateReportCmd implements Command {
    CommandId id;
    ReportID reportId;
    UserID creatorId;
    Date createdAt;


    @Override
    public CommandId getId() {
        return id;
    }

    public ValidationResult validate(List<String> problems) {
        if(id == null) problems.add("Command id is null");
        if(reportId == null) problems.add("Report id is null");
        if(creatorId == null) problems.add("Creator id is null");
        return new ValidationResult(problems);
    }

}

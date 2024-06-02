package com.hexi.Cerberus.domain.report;

import com.hexi.Cerberus.application.report.service.command.create.CreateReportCmd;
import org.springframework.stereotype.Component;

@Component
public interface ReportFactory {
    Report from(CreateReportCmd build);
}

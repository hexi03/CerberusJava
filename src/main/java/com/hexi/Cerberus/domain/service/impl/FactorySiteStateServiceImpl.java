package com.hexi.Cerberus.domain.service.impl;

import com.hexi.Cerberus.domain.factorysite.FactorySite;
import com.hexi.Cerberus.domain.factorysite.FactorySiteState;
import com.hexi.Cerberus.domain.item.repository.ItemRepository;
import com.hexi.Cerberus.domain.report.factorysite.FactorySiteReport;
import com.hexi.Cerberus.domain.report.query.filter.FactorySiteReportFilterCriteria;
import com.hexi.Cerberus.domain.report.query.filter.ReportStatus;
import com.hexi.Cerberus.domain.report.query.filter.WareHouseReportFilterCriteria;
import com.hexi.Cerberus.domain.report.repository.ReportRepository;
import com.hexi.Cerberus.domain.report.warehouse.WareHouseReport;
import com.hexi.Cerberus.domain.service.FactorySiteStateService;
import com.hexi.Cerberus.domain.warehouse.WareHouse;
import com.hexi.Cerberus.infrastructure.StateProblem;
import com.hexi.Cerberus.infrastructure.StateWarning;
import com.hexi.Cerberus.infrastructure.query.Query;
import com.hexi.Cerberus.infrastructure.query.comparation.ComparationContainer;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;



@RequiredArgsConstructor
public class FactorySiteStateServiceImpl implements FactorySiteStateService {
    public final ReportRepository reportRepository;
    public final ItemRepository itemRepository;
    public List<FactorySiteReport> getReports(FactorySite factorySite, Date treshold){
        FactorySiteReportFilterCriteria filter = FactorySiteReportFilterCriteria
                .builder()
                .date(new ComparationContainer<>(treshold, ComparationContainer.Sign.GREATEREQUAL))
                .status(ReportStatus.ACTIVE)
                .department(factorySite.getParentDepartment().getId())
                .factorySite(factorySite.getId())
                .build();
        return reportRepository.displayAll(new Query(filter, null,null)).stream().map(report -> (FactorySiteReport) report).collect(Collectors.toList());
    }
    public List<WareHouseReport> getReports(WareHouse wareHouse){
        WareHouseReportFilterCriteria filter = WareHouseReportFilterCriteria
                .builder()
                .status(ReportStatus.ACTIVE)
                .department(wareHouse.getParentDepartment().getId())
                .warehouse(wareHouse.getId())
                .build();
        return reportRepository.displayAll(new Query(filter, null,null)).stream().map(report -> (WareHouseReport) report).collect(Collectors.toList());
    }

    @Override
    public FactorySiteState getFactorySiteState(FactorySite factorySite) {

        List<StateProblem> problems = getProblems(factorySite);
        List<StateWarning> warnings = getWarnings(factorySite);



        return FactorySiteState
                .builder()
                .problems(problems)
                .warnings(warnings)
                .build();

    }

    private List<StateWarning> getWarnings(FactorySite factorySite) {
        List<StateWarning> warnings = new ArrayList<>();
        //TODO

        return warnings;
    }

    private List<StateProblem> getProblems(FactorySite factorySite) {
        List<StateProblem> problems = new ArrayList<>();
        //TODO

        return problems;
    }
}

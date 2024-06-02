package com.hexi.Cerberus.domain.service.impl;

import com.hexi.Cerberus.domain.factorysite.FactorySite;
import com.hexi.Cerberus.domain.factorysite.FactorySiteState;
import com.hexi.Cerberus.domain.helpers.ItemMapHelper;
import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.item.repository.ItemRepository;
import com.hexi.Cerberus.domain.item.service.ItemRegistriesQueryService;
import com.hexi.Cerberus.domain.report.factorysite.FactorySiteReport;
import com.hexi.Cerberus.domain.report.factorysite.WorkShiftReport;
import com.hexi.Cerberus.domain.report.query.filter.FactorySiteReportFilterCriteria;
import com.hexi.Cerberus.domain.report.query.filter.WareHouseReportFilterCriteria;
import com.hexi.Cerberus.domain.report.query.filter.WorkShiftReportFilterCriteria;
import com.hexi.Cerberus.domain.report.repository.ReportRepository;
import com.hexi.Cerberus.domain.report.warehouse.WareHouseReport;
import com.hexi.Cerberus.domain.service.FactorySiteStateService;
import com.hexi.Cerberus.domain.service.problems.WorkShiftConsumablesLossProblem;
import com.hexi.Cerberus.domain.service.problems.WorkShiftConsumablesTooMuchProblem;
import com.hexi.Cerberus.domain.service.warnings.WorkShiftLossesWarning;
import com.hexi.Cerberus.domain.service.warnings.WorkShiftRemainsWarning;
import com.hexi.Cerberus.domain.warehouse.WareHouse;
import com.hexi.Cerberus.infrastructure.StateProblem;
import com.hexi.Cerberus.infrastructure.StateWarning;
import com.hexi.Cerberus.infrastructure.query.Query;
import com.hexi.Cerberus.infrastructure.query.comparation.ComparisonContainer;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class FactorySiteStateServiceImpl implements FactorySiteStateService {
    public final ReportRepository reportRepository;
    public final ItemRepository itemRepository;
    public final ItemRegistriesQueryService itemRegistriesQueryService;

    public List<FactorySiteReport> getReports(FactorySite factorySite, Date treshold) {
        FactorySiteReportFilterCriteria filter = FactorySiteReportFilterCriteria
                .builder()
                .createdDate(new ComparisonContainer<>(treshold, ComparisonContainer.Sign.GREATEREQUAL))
                .isDeleted(false)
                //.status(ReportStatus.ACTIVE)
                .departmentId(factorySite.getParentDepartment().getId())
                .factorySiteId(factorySite.getId())
                .build();
        return (List<FactorySiteReport>) reportRepository.findAllWithQuery(new Query(FactorySiteReport.class, filter, null, null)).stream().map(report -> report).collect(Collectors.toList());
    }

    public List<WareHouseReport> getReports(WareHouse wareHouse) {
        WareHouseReportFilterCriteria filter = WareHouseReportFilterCriteria
                .builder()
                .isDeleted(false)
                //.status(ReportStatus.ACTIVE)
                .departmentId(wareHouse.getParentDepartment().getId())
                .warehouseId(wareHouse.getId())
                .build();
        return (List<WareHouseReport>) reportRepository.findAllWithQuery(new Query(FactorySiteReport.class, filter, null, null)).stream().map(report -> report).collect(Collectors.toList());
    }

    private List<WorkShiftReport> getWorkShiftReportsWithLosses(FactorySite factorySite) {
        WorkShiftReportFilterCriteria filter = WorkShiftReportFilterCriteria
                .builder()
                .isDeleted(false)
                .hasLosses(true)
                //.createdDate(new ComparisonContainer<>(treshold, ComparisonContainer.Sign.GREATEREQUAL))
                //.status(ReportStatus.ACTIVE)
                .departmentId(factorySite.getParentDepartment().getId())
                .factorySiteId(factorySite.getId())
                .build();
        return (List<WorkShiftReport>) reportRepository.findAllWithQuery(new Query(WorkShiftReport.class, filter, null, null)).stream().map(report -> report).collect(Collectors.toList());

    }

    private List<WorkShiftReport> getWorkShiftReportsWithRemains(FactorySite factorySite) {
        WorkShiftReportFilterCriteria filter = WorkShiftReportFilterCriteria
                .builder()
                .isDeleted(false)
                .hasRemains(true)
                //.createdDate(new ComparisonContainer<>(treshold, ComparisonContainer.Sign.GREATEREQUAL))
                //.status(ReportStatus.ACTIVE)
                .departmentId(factorySite.getParentDepartment().getId())
                .factorySiteId(factorySite.getId())
                .build();
        return (List<WorkShiftReport>) reportRepository.findAllWithQuery(new Query(WorkShiftReport.class, filter, null, null)).stream().map(report -> report).collect(Collectors.toList());

    }

    private Map<ItemID, Integer> getFactorySiteLostedOnSiteConsumables(FactorySite factorySite) {
        return itemRegistriesQueryService.getFactorySiteLostedOnSiteConsumables(factorySite);

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
        //Есть потери РМ

        List<WorkShiftReport> reportsWithLosses = getWorkShiftReportsWithLosses(factorySite);
        for (WorkShiftReport rep : reportsWithLosses)
            warnings.add(new WorkShiftLossesWarning(rep.getId(),rep.getLosses().entrySet().stream().collect(Collectors.toMap(entry -> entry.getKey().getId(), entry -> entry.getValue()))));

        //Есть остатки на территории FS
        List<WorkShiftReport> reportsWithRemains = getWorkShiftReportsWithRemains(factorySite);
        for (WorkShiftReport rep : reportsWithRemains) {
            warnings.add(new WorkShiftRemainsWarning(rep.getId(),rep.getRemains().entrySet().stream().collect(Collectors.toMap(entry -> entry.getKey().getId(), entry -> entry.getValue()))));
        }

        return warnings;
    }



    private List<StateProblem> getProblems(FactorySite factorySite) {
        List<StateProblem> problems = new ArrayList<>();
        //Выявлена незадекларированная потеря РМ
        Map<ItemID, Integer> lostedOnSiteConsumables = getFactorySiteLostedOnSiteConsumables(factorySite);
        Map<ItemID, Integer> lostedOnSiteConsumablesPos = ItemMapHelper.filterPos(lostedOnSiteConsumables);
        Map<ItemID, Integer> lostedOnSiteConsumablesNeg = ItemMapHelper.filterNeg(lostedOnSiteConsumables);

        if(!lostedOnSiteConsumablesPos.isEmpty())
                problems.add(new WorkShiftConsumablesLossProblem(lostedOnSiteConsumablesPos));

        if(!lostedOnSiteConsumablesNeg.isEmpty())
            problems.add(new WorkShiftConsumablesTooMuchProblem(lostedOnSiteConsumablesNeg));


        return problems;
    }




}

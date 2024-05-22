package com.hexi.Cerberus.domain.service.impl;

import com.hexi.Cerberus.domain.helpers.ItemMapHelper;
import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.item.repository.ItemRepository;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.domain.report.query.filter.InventarisationReportFilterCriteria;
import com.hexi.Cerberus.domain.report.query.filter.WareHouseReportFilterCriteria;
import com.hexi.Cerberus.domain.report.query.sort.InventarisationReportSortCriteria;
import com.hexi.Cerberus.domain.report.query.sort.ReportSortCriteria;
import com.hexi.Cerberus.domain.report.repository.ReportRepository;
import com.hexi.Cerberus.domain.report.service.ReportQueryService;
import com.hexi.Cerberus.domain.report.warehouse.InventarisationReport;
import com.hexi.Cerberus.domain.report.warehouse.ItemRelease;
import com.hexi.Cerberus.domain.report.warehouse.ItemReplenish;
import com.hexi.Cerberus.domain.report.warehouse.WareHouseReport;
import com.hexi.Cerberus.domain.service.WareHouseStateService;
import com.hexi.Cerberus.domain.service.problems.InvalidStorageStateProblem;
import com.hexi.Cerberus.domain.service.problems.InventarisationReportProblem;
import com.hexi.Cerberus.domain.service.problems.ReleasedTooMuchReportProblem;
import com.hexi.Cerberus.domain.service.warnings.UnsatisfiedSupplyRequirementReportWarning;
import com.hexi.Cerberus.domain.service.warnings.UnsatisfiedWorkShiftReportWarning;
import com.hexi.Cerberus.domain.service.warnings.WorkShiftReplenishedTooMuchReportWarning;
import com.hexi.Cerberus.domain.warehouse.WareHouse;
import com.hexi.Cerberus.domain.warehouse.WareHouseID;
import com.hexi.Cerberus.domain.warehouse.WareHouseState;
import com.hexi.Cerberus.domain.warehouse.repository.WareHouseRepository;
import com.hexi.Cerberus.infrastructure.StateProblem;
import com.hexi.Cerberus.infrastructure.StateWarning;
import com.hexi.Cerberus.infrastructure.query.PagingCriteria;
import com.hexi.Cerberus.infrastructure.query.Query;
import com.hexi.Cerberus.infrastructure.query.comparation.ComparisonContainer;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
@Transactional
public class WareHouseStateServiceImpl implements WareHouseStateService {
    public final ReportRepository reportRepository;
    public final ItemRepository itemRepository;
    public final WareHouseRepository wareHouseRepository;
    public final ReportQueryService reportQueryService;

    public List<WareHouseReport> getReports(WareHouseID wareHouseId, Optional<Date> treshold) {
        return getReports(wareHouseId, treshold, Optional.empty());
    }

    public List<WareHouseReport> getReports(WareHouseID wareHouseId, Optional<Date> treshold, Optional<ReportID> subsequentId) {
        WareHouseReportFilterCriteria filter = WareHouseReportFilterCriteria
                .builder()
                .createdDate(treshold.isPresent() ? new ComparisonContainer<Date>(treshold.get(), ComparisonContainer.Sign.GREATEREQUAL) : null)
                //.status(ReportStatus.ACTIVE)
                //.department(wareHouse.getParentDepartment().getId())
                .warehouseId(wareHouseId)
                .build();
        return (List<WareHouseReport>) reportRepository.findAllWithQuery(new Query(WareHouseReport.class, filter, null, new PagingCriteria(subsequentId.orElse(null), null))).stream().collect(Collectors.toList());
    }

    private List<InventarisationReport> getInventarisationReports(WareHouseID id, ReportSortCriteria.SortType sortType) {
        InventarisationReportFilterCriteria filter = InventarisationReportFilterCriteria
                .builder()
                //.createdDate(treshold.isPresent() ? new ComparisonContainer<Date>(treshold.get(), ComparisonContainer.Sign.GREATEREQUAL) : null)
                //.status(ReportStatus.ACTIVE)
                //.department(wareHouse.getParentDepartment().getId())
                .warehouseId(id)
                .build();

        InventarisationReportSortCriteria sortCriteria = InventarisationReportSortCriteria
                .builder()
                .sortBy(List.of(ReportSortCriteria.SortBy.CREATED))
                .sortType(List.of(sortType))
                .build();
        return (List<InventarisationReport>) reportRepository.findAllWithQuery(new Query(InventarisationReport.class, filter, sortCriteria, null)).stream().collect(Collectors.toList());
    }
    public List<WareHouseReport> getReports(WareHouse wareHouse) {

        return getReports(wareHouse.getId(), Optional.empty());
    }

    public List<WareHouseReport> getReports(WareHouseID wareHouseId) {

        return getReports(wareHouseId, Optional.empty());
    }

    public List<WareHouseReport> getReports(WareHouse wareHouse, Optional<Date> treshold) {

        return getReports(wareHouse.getId(), treshold);
    }



    public Map<ItemID, Integer> getStorageState(WareHouse wareHouse) {

        Optional<InventarisationReport> inventarisationBase = getLastInventarisationReport(wareHouse);

        return getStorageState(wareHouse.getId(), inventarisationBase);

    }

    private Map<ItemID, Integer> getStorageState(WareHouseID wareHouse, Optional<InventarisationReport> inventarisationBase){
        return getStorageState(wareHouse, inventarisationBase, Optional.empty());
    }
    private Map<ItemID, Integer> getStorageState(WareHouseID wareHouse, Optional<InventarisationReport> inventarisationBase, Optional<ReportID> subsequentId) {
        Map<ItemID, Integer> baseStorageState;
        Date dateTreshold;
        List<WareHouseReport> wareHouseReports;

        if (inventarisationBase.isPresent()) {
            baseStorageState =
                    getInventarisedItems((inventarisationBase
                            .get()));
            dateTreshold = inventarisationBase.get().getCreatedAt();
            wareHouseReports = getReports(wareHouse, Optional.of(dateTreshold), subsequentId);
        } else {
            baseStorageState = new HashMap<>();
            //dateTreshold = null;
            wareHouseReports = getReports(wareHouse);
        }


        Map<ItemID, Integer> itemReplenishes =
                getReplenishes(wareHouseReports); //fetch by marking interface
        Map<ItemID, Integer> itemReleases =
                getReleases(wareHouseReports); //fetch by marking interface

        return ItemMapHelper.MergeDictionariesWithSub(
                ItemMapHelper.MergeDictionariesWithSum(baseStorageState, itemReplenishes),
                itemReleases
        );
    }

    private static Map<ItemID, Integer> getInventarisedItems(InventarisationReport inventarisationBase) {
        return
                inventarisationBase.getItems()
                .entrySet()
                .stream()
                .map(itemIntegerEntry -> new AbstractMap.SimpleImmutableEntry<ItemID, Integer>(itemIntegerEntry.getKey().getId(), itemIntegerEntry.getValue()))
                .collect(Collectors.toMap(AbstractMap.SimpleImmutableEntry::getKey, AbstractMap.SimpleImmutableEntry::getValue, Integer::sum));
    }

    private Optional<InventarisationReport> getLastInventarisationReport(WareHouse wareHouse){
        return getLastInventarisationReport(wareHouse, Optional.empty());
    }
    private Optional<InventarisationReport> getLastInventarisationReport(WareHouse wareHouse, Optional<ReportID> subsequentInvId) {
        InventarisationReportFilterCriteria inventarisationReportFilter = InventarisationReportFilterCriteria
                .builder()
                //.status(ReportStatus.ACTIVE)
                .departmentId(wareHouse.getParentDepartment().getId())
                .warehouseId(wareHouse.getId())
                .build();

        ReportSortCriteria inventarisationReportSort = ReportSortCriteria.builder().sortBy(List.of(ReportSortCriteria.SortBy.CREATED)).sortType(List.of(ReportSortCriteria.SortType.DESCENDING)).build();


        Optional<InventarisationReport> inventarisationBase = reportRepository.findAllWithQuery(new Query(InventarisationReport.class,inventarisationReportFilter, inventarisationReportSort, new PagingCriteria(subsequentInvId.orElse(null), 1))).stream().findFirst();
        return inventarisationBase;
    }

    private static Map<ItemID, Integer> getReplenishes(List<WareHouseReport> wareHouseReports) {
        return wareHouseReports
                .stream()
                .filter(wareHouseReport -> wareHouseReport instanceof ItemReplenish)
                .map(wareHouseReport -> (ItemReplenish) wareHouseReport)
                .reduce(
                        (List<AbstractMap.SimpleImmutableEntry<ItemID, Integer>>) (new ArrayList<AbstractMap.SimpleImmutableEntry<ItemID, Integer>>()),
                        (arrayList, itemReplenish) -> (
                                itemReplenish
                                        .getItems()
                                        .entrySet()
                                        .stream()
                                        .map(
                                                itemIntegerEntry -> new AbstractMap.SimpleImmutableEntry<ItemID, Integer>(itemIntegerEntry.getKey().getId(), itemIntegerEntry.getValue())
                                        )
                                        .toList()
                        ),
                        (arrayList, arrayList2) -> {
                            arrayList.addAll(arrayList2);
                            return arrayList;
                        })
                .stream()
                .collect(Collectors.toMap(AbstractMap.SimpleImmutableEntry::getKey, AbstractMap.SimpleImmutableEntry::getValue, Integer::sum));
    }

    private static Map<ItemID, Integer> getReleases(List<WareHouseReport> wareHouseReports) {
        return wareHouseReports
                .stream()
                .filter(wareHouseReport -> wareHouseReport instanceof ItemRelease)
                .map(wareHouseReport -> (ItemRelease) wareHouseReport)
                .reduce(
                        (List<AbstractMap.SimpleImmutableEntry<ItemID, Integer>>) (new ArrayList<AbstractMap.SimpleImmutableEntry<ItemID, Integer>>()),
                        (arrayList, itemReplenish) -> (
                                itemReplenish
                                        .getItems()
                                        .entrySet()
                                        .stream()
                                        .map(
                                                itemIntegerEntry -> new AbstractMap.SimpleImmutableEntry<ItemID, Integer>(itemIntegerEntry.getKey().getId(), itemIntegerEntry.getValue())
                                        )
                                        .toList()
                        ),
                        (arrayList, arrayList2) -> {
                            arrayList.addAll(arrayList2);
                            return arrayList;
                        })
                .stream()
                .collect(Collectors.toMap(AbstractMap.SimpleImmutableEntry::getKey, AbstractMap.SimpleImmutableEntry::getValue, Integer::sum));
    }


    @Override
    public WareHouseState getWareHouseState(WareHouse wareHouse) {

        Map<ItemID, Integer> storageStateItemID = getStorageState(wareHouse);
        List<ItemID> itemIDS = storageStateItemID.entrySet().stream().map(itemIDIntegerEntry -> itemIDIntegerEntry.getKey()).toList();
        List<Item> items = itemRepository.findAllById(itemIDS).stream().toList();
        Map<Item, Integer> storageState = items.stream().collect(Collectors.toMap(item -> item, item -> storageStateItemID.get(item.getId()), Integer::sum));

        List<StateProblem> problems = getProblems(wareHouse);
        List<StateWarning> warnings = getWarnings(wareHouse);


        return WareHouseState
                .builder()
                .items(storageState)
                .problems(problems)
                .warnings(warnings)
                .build();


    }

    private List<StateWarning> getWarnings(WareHouse wareHouse) {
        List<StateWarning> warnings = new ArrayList<>();

        // Предупреждение: Нет отчета или недостаточно об отпуске РМ при наличии отчета о запросе РМ
        List<ReportID> unsatisfiedSupplyRequirementReportsPos =
                findUnsatisfiedSupplyRequirementReports(wareHouse)
                        .entrySet()
                        .stream()
                        .filter(reportIDMapEntry -> ItemMapHelper.filterPos(reportIDMapEntry.getValue()).size() > 0)
                        .map(reportIDMapEntry -> reportIDMapEntry.getKey())
                        .collect(Collectors.toList());
        if(!unsatisfiedSupplyRequirementReportsPos.isEmpty())
            for (ReportID rep : unsatisfiedSupplyRequirementReportsPos)
                warnings.add(new UnsatisfiedSupplyRequirementReportWarning(rep));


        // Предупреждение: Нет отчета или недостаточно о поступлении при наличии отчета о завершении рабочей смены с пополнением

        List<ReportID> unsatisfiedWorkShiftByProducedReportsPos =
                findUnsatisfiedByReplenishedProducedWorkShiftReports(wareHouse)
                        .entrySet()
                        .stream()
                        .filter(reportIDMapEntry -> ItemMapHelper.filterPos(reportIDMapEntry.getValue()).size() > 0)
                        .map(reportIDMapEntry -> reportIDMapEntry.getKey())
                        .collect(Collectors.toList());
        if(!unsatisfiedWorkShiftByProducedReportsPos.isEmpty())
            for (ReportID rep : unsatisfiedWorkShiftByProducedReportsPos)
                warnings.add(new UnsatisfiedWorkShiftReportWarning(rep, UnsatisfiedWorkShiftReportWarning.By.Produced));


        List<ReportID> unsatisfiedWorkShiftByUnclaimedRemainsReportsPos =
                findUnsatisfiedByReplenishedUnclaimedRemainsWorkShiftReports(wareHouse)
                        .entrySet()
                        .stream()
                        .filter(reportIDMapEntry -> ItemMapHelper.filterPos(reportIDMapEntry.getValue()).size() > 0)
                        .map(reportIDMapEntry -> reportIDMapEntry.getKey())
                        .collect(Collectors.toList());
        if(!unsatisfiedWorkShiftByUnclaimedRemainsReportsPos.isEmpty())
            for (ReportID rep : unsatisfiedWorkShiftByUnclaimedRemainsReportsPos)
                warnings.add(new UnsatisfiedWorkShiftReportWarning(rep, UnsatisfiedWorkShiftReportWarning.By.UnclaimedRemains));




        return warnings;
    }

//    private List<WorkShiftReport> (WareHouse wareHouse) {
//        return null;
//    }

    public Map<ReportID, Map<ItemID, Integer>> findUnsatisfiedByReplenishedProducedWorkShiftReports(WareHouse wareHouse) {
        return reportQueryService.findUnsatisfiedByReplenishedProducedWorkShiftReports(wareHouse);
    }
    public Map<ReportID, Map<ItemID, Integer>> findUnsatisfiedSupplyRequirementReports(WareHouse wareHouse) {
        return reportQueryService.findUnsatisfiedSupplyRequirementReports(wareHouse);
    }
    public Map<ReportID, Map<ItemID, Integer>> findUnsatisfiedByReplenishedUnclaimedRemainsWorkShiftReports(WareHouse wareHouse) {
        return reportQueryService.findUnsatisfiedByReplenishedUnclaimedRemainsWorkShiftReports(wareHouse);
    }
//    private List<SupplyRequirementReport> getUnsatisfiedSupplyRequirementReports(WareHouse wareHouse) {
//        return null;
//    }

    private List<StateProblem> getProblems(WareHouse wareHouse) {
        List<StateProblem> problems = new ArrayList<>();


        // Проблема: Отрицательные значения в учете (невозможное состояние)
        Map<ItemID, Integer> storageState = getStorageState(wareHouse);
        Map<ItemID, Integer> invalidStorageStatePositions =
                storageState
                        .entrySet()
                        .stream()
                        .filter(e -> e.getValue() < 0)
                        .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));

        if(!invalidStorageStatePositions.isEmpty())
            problems.add(new InvalidStorageStateProblem(invalidStorageStatePositions));

        //Проблема: Выдано/забрано больше чем просили
        List<ReportID> unsatisfiedSupplyRequirementReportsPos =
                findUnsatisfiedSupplyRequirementReports(wareHouse)
                        .entrySet()
                        .stream()
                        .filter(reportIDMapEntry -> ItemMapHelper.filterNeg(reportIDMapEntry.getValue()).size() > 0)
                        .map(reportIDMapEntry -> reportIDMapEntry.getKey())
                        .collect(Collectors.toList());
        if(!unsatisfiedSupplyRequirementReportsPos.isEmpty())
            for (ReportID rep : unsatisfiedSupplyRequirementReportsPos)
                problems.add(new ReleasedTooMuchReportProblem(rep));


        List<ReportID> unsatisfiedWorkShiftByProducedReportsPos =
                findUnsatisfiedByReplenishedProducedWorkShiftReports(wareHouse)
                        .entrySet()
                        .stream()
                        .filter(reportIDMapEntry -> ItemMapHelper.filterNeg(reportIDMapEntry.getValue()).size() > 0)
                        .map(reportIDMapEntry -> reportIDMapEntry.getKey())
                        .collect(Collectors.toList());
        if(!unsatisfiedWorkShiftByProducedReportsPos.isEmpty())
            for (ReportID rep : unsatisfiedWorkShiftByProducedReportsPos)
                problems.add(new WorkShiftReplenishedTooMuchReportWarning(rep, WorkShiftReplenishedTooMuchReportWarning.By.Produced));


        List<ReportID> unsatisfiedWorkShiftByUnclaimedRemainsReportsPos =
                findUnsatisfiedByReplenishedUnclaimedRemainsWorkShiftReports(wareHouse)
                        .entrySet()
                        .stream()
                        .filter(reportIDMapEntry -> ItemMapHelper.filterNeg(reportIDMapEntry.getValue()).size() > 0)
                        .map(reportIDMapEntry -> reportIDMapEntry.getKey())
                        .collect(Collectors.toList());
        if(!unsatisfiedWorkShiftByUnclaimedRemainsReportsPos.isEmpty())
            for (ReportID rep : unsatisfiedWorkShiftByUnclaimedRemainsReportsPos)
                problems.add(new WorkShiftReplenishedTooMuchReportWarning(rep, WorkShiftReplenishedTooMuchReportWarning.By.UnclaimedRemains));

        //TODO Проблема: Данные инвентаризации не совпали с ожидаемыми
        List<InventarisationReport> inventarisationReports = getInventarisationReports(wareHouse.getId(), ReportSortCriteria.SortType.DESCENDING);
        Iterator invIter = inventarisationReports.iterator();
        if (invIter.hasNext()) {
            InventarisationReport currInvRep = (InventarisationReport) invIter.next();
            while (invIter.hasNext()) {
                Optional<InventarisationReport> prevInvRep = getLastInventarisationReport(wareHouse, Optional.of(currInvRep.getId()));
                Map<ItemID, Integer> inventarisedStorageState = getInventarisedItems(currInvRep);
                Map<ItemID, Integer> expectedStorageState = getStorageState(wareHouse.getId(), prevInvRep, Optional.of(currInvRep.getId()));
                Map<ItemID, Integer> differrence = ItemMapHelper.filterNonZero(ItemMapHelper.MergeDictionariesWithSub(expectedStorageState, inventarisedStorageState));
                if (!differrence.isEmpty())
                    problems.add(new InventarisationReportProblem(differrence));
                currInvRep = (InventarisationReport) invIter.next();
            }
        }
        return problems;
    }




}

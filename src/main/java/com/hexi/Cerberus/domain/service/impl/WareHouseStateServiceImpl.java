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
import com.hexi.Cerberus.infrastructure.query.comparation.ComparisonSequence;
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

    public List<WareHouseReport> getReports(WareHouseID wareHouseId, Date lowerTreshold) {
        return getReports(wareHouseId, lowerTreshold, null);
    }

    public List<WareHouseReport> getReports(WareHouseID wareHouseId, Date lowerTreshold, Date upperTreshold) {
        ComparisonSequence<Date> createdDateSeq = null;
        if (lowerTreshold != null || upperTreshold != null){
            List<ComparisonContainer<Date>> createdDateContainers = new ArrayList<>();
            if (upperTreshold != null) createdDateContainers.add(new ComparisonContainer<Date>(upperTreshold, ComparisonContainer.Sign.LESS));
            if (lowerTreshold != null) createdDateContainers.add(new ComparisonContainer<Date>(lowerTreshold, ComparisonContainer.Sign.GREATER));
            createdDateSeq = new ComparisonSequence<>(createdDateContainers);
        }

        WareHouseReportFilterCriteria filter = WareHouseReportFilterCriteria
                .builder()
                .isDeleted(false)
                .createdDate(createdDateSeq)
                //.status(ReportStatus.ACTIVE)
                //.department(wareHouse.getParentDepartment().getId())
                .warehouseId(wareHouseId)
                .build();
        return (List<WareHouseReport>) reportRepository.findAllWithQuery(new Query(WareHouseReport.class, filter, null, null)).stream().collect(Collectors.toList());
    }

    private List<InventarisationReport> getInventarisationReports(WareHouseID id, ReportSortCriteria.SortType sortType) {
        InventarisationReportFilterCriteria filter = InventarisationReportFilterCriteria
                .builder()
                .isDeleted(false)
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

        return getReports(wareHouse.getId(), null);
    }

    public List<WareHouseReport> getReports(WareHouseID wareHouseId) {

        return getReports(wareHouseId, null);
    }

    public List<WareHouseReport> getReports(WareHouse wareHouse, Date treshold) {

        return getReports(wareHouse.getId(), treshold);
    }



    public Map<ItemID, Integer> getStorageState(WareHouse wareHouse) {

        Optional<InventarisationReport> inventarisationBase = getLastInventarisationReport(wareHouse);

        return getStorageState(wareHouse.getId(), inventarisationBase);

    }

    private Map<ItemID, Integer> getStorageState(WareHouseID wareHouse, Optional<InventarisationReport> inventarisationBase){
        return getStorageState(wareHouse, inventarisationBase, null);
    }
    private Map<ItemID, Integer> getStorageState(WareHouseID wareHouse, Optional<InventarisationReport> inventarisationBase, Date upperTreshold) {
        Map<ItemID, Integer> baseStorageState;
        Date dateTreshold;
        List<WareHouseReport> wareHouseReports;
        System.out.println("    Вычисление состояния: ");

        if (inventarisationBase.isPresent()) {
            System.out.println(String.format("  Используется база инвентаризации от %s: ", inventarisationBase.get().getCreatedAt().toString()));
            baseStorageState =
                    getInventarisedItems((inventarisationBase
                            .get()));
            System.out.println("    Перечень инвентаризованного по базе: ");
            System.out.println(baseStorageState);
            dateTreshold = inventarisationBase.get().getCreatedAt();
            wareHouseReports = getReports(wareHouse, dateTreshold, upperTreshold); // TODO time sort needs
        } else {
            System.out.println("    База инвентаризации не используется");
            baseStorageState = new HashMap<>();
            //dateTreshold = null;
            wareHouseReports = getReports(wareHouse, null, upperTreshold);
        }
        System.out.println("    Совокупность отчетов склада по запросу: ");
        System.out.println(wareHouseReports.stream().map(wareHouseReport -> wareHouseReport.getId().getId().toString()).collect(Collectors.toList()));

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

    private Optional<InventarisationReport> getLastInventarisationReport(WareHouse wareHouse) {
        return getLastInventarisationReport(wareHouse, null);
    }
    private Optional<InventarisationReport> getLastInventarisationReport(WareHouse wareHouse, Date beforeDate) {
        InventarisationReportFilterCriteria inventarisationReportFilter = InventarisationReportFilterCriteria
                .builder()
                .isDeleted(false)
                .createdDate(beforeDate == null ? null : new ComparisonContainer<>(beforeDate, ComparisonContainer.Sign.LESS))
                //.status(ReportStatus.ACTIVE)
                //.departmentId(wareHouse.getParentDepartment().getId())
                .warehouseId(wareHouse.getId())
                .build();

        ReportSortCriteria inventarisationReportSort = ReportSortCriteria.builder().sortBy(List.of(ReportSortCriteria.SortBy.CREATED)).sortType(List.of(ReportSortCriteria.SortType.DESCENDING)).build();


        Optional<InventarisationReport> inventarisationBase = reportRepository.findAllWithQuery(new Query(InventarisationReport.class,inventarisationReportFilter, inventarisationReportSort, null)).stream().findFirst();
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
                                        .getSummaryReplenish()
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
                                        .getSummaryRelease()
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

        //Проблема: Данные инвентаризации не совпали с ожидаемыми
        System.out.println("Проверка отчетов об инвентаризации: ");
        List<InventarisationReport> inventarisationReports = getInventarisationReports(wareHouse.getId(), ReportSortCriteria.SortType.DESCENDING);
        Iterator invIter = inventarisationReports.iterator();
        System.out.println(String.format("Вытащились %d отчетов: ", inventarisationReports.size()));
        System.out.println(inventarisationReports);
        for (InventarisationReport currInvRep : inventarisationReports){
                System.out.println(String.format("Инвентаризация от %s: ", currInvRep.getCreatedAt().toString()));
                Optional<InventarisationReport> prevInvRep = getLastInventarisationReport(wareHouse, currInvRep.getCreatedAt());
                Map<ItemID, Integer> inventarisedStorageState = getInventarisedItems(currInvRep);
                System.out.println("Перечень инвентаризованного: ");
                System.out.println(inventarisedStorageState);
                Map<ItemID, Integer> expectedStorageState = getStorageState(wareHouse.getId(), prevInvRep, currInvRep.getCreatedAt());
                System.out.println("Ожидаемое состояние: ");
                System.out.println(expectedStorageState);
                Map<ItemID, Integer> differrence = ItemMapHelper.filterNonZero(ItemMapHelper.MergeDictionariesWithSub(expectedStorageState, inventarisedStorageState));
                System.out.println("Разница: ");
                System.out.println(differrence);
                if (!differrence.isEmpty())
                    problems.add(new InventarisationReportProblem(currInvRep.getId(),differrence));

        }
        return problems;
    }




}

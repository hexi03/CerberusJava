package com.hexi.Cerberus.domain.service.impl;

import com.hexi.Cerberus.domain.helpers.ItemMapHelper;
import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.item.repository.ItemRepository;
import com.hexi.Cerberus.domain.report.Report;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.domain.report.query.filter.InventarisationReportFilterCriteria;
import com.hexi.Cerberus.domain.report.query.filter.ReportStatus;
import com.hexi.Cerberus.domain.report.query.filter.WareHouseReportFilterCriteria;
import com.hexi.Cerberus.domain.report.query.sort.ReportSortCriteria;
import com.hexi.Cerberus.domain.report.repository.ReportRepository;
import com.hexi.Cerberus.domain.report.warehouse.InventarisationReport;
import com.hexi.Cerberus.domain.report.warehouse.ItemRelease;
import com.hexi.Cerberus.domain.report.warehouse.ItemReplenish;
import com.hexi.Cerberus.domain.report.warehouse.WareHouseReport;
import com.hexi.Cerberus.domain.service.WareHouseStateService;
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
    public WareHouseRepository wareHouseRepository;

    public List<WareHouseReport> getReports(WareHouseID wareHouseId, Optional<Date> treshold) {
        WareHouseReportFilterCriteria filter = WareHouseReportFilterCriteria
                .builder()
                .createdDate(treshold.isPresent() ? new ComparisonContainer<Date>(treshold.get(), ComparisonContainer.Sign.GREATEREQUAL) : null)
                .status(ReportStatus.ACTIVE)
                //.department(wareHouse.getParentDepartment().getId())
                .warehouseId(wareHouseId)
                .build();
        return (List<WareHouseReport>) reportRepository.findAllWithQuery(new Query(WareHouse.class, filter, null, null)).stream().collect(Collectors.toList());
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

        Optional<Report> inventarisationBase = getLastInventarisationReport(wareHouse, Optional.empty());

        return getStorageState(wareHouse.getId(), inventarisationBase);

    }

    private Map<ItemID, Integer> getStorageState(WareHouseID wareHouse, Optional<Report> inventarisationBase) {
        Map<ItemID, Integer> baseStorageState;
        Date dateTreshold;
        List<WareHouseReport> wareHouseReports;

        if (inventarisationBase.isPresent()) {
            baseStorageState =
                    getInventarisedItems(((InventarisationReport) inventarisationBase
                            .get()));
            dateTreshold = inventarisationBase.get().getCreatedAt();
            wareHouseReports = getReports(wareHouse, Optional.of(dateTreshold));
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

    private Optional<Report> getLastInventarisationReport(WareHouse wareHouse, Optional<ReportID> subsequentInvId) {
        InventarisationReportFilterCriteria inventarisationReportFilter = InventarisationReportFilterCriteria
                .builder()
                .status(ReportStatus.ACTIVE)
                .department(wareHouse.getParentDepartment().getId())
                .warehouseId(wareHouse.getId())
                .build();

        ReportSortCriteria inventarisationReportSort = ReportSortCriteria.builder().sortBy(List.of(ReportSortCriteria.SortBy.CREATED)).sortType(List.of(ReportSortCriteria.SortType.DESCENDING)).build();


        Optional<Report> inventarisationBase = reportRepository.findAllWithQuery(new Query(InventarisationReport.class,inventarisationReportFilter, inventarisationReportSort, new PagingCriteria(subsequentInvId.orElse(null), 1))).stream().findFirst();
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

//
//
//        // Предупреждение: Нет отчета о поступлении РМ при наличии отчета о реализации РМ
//
//
//
//        // Предупреждение: Нет отчета о поступлении при наличии отчета о завершении рабочей смены с пополнением
//        List<Report> workShiftReplenishmentReports = getAllWorkShiftReplenishmentReports(wareHouse);
//        List<Report> shipmentReports = getAllShipmentReports(wareHouse);
//        if (!shipmentReports.isEmpty() && workShiftReplenishmentReports.isEmpty()) {
//            warnings.add(new StateWarning("Нет отчета о поступлении при наличии отчета о завершении рабочей смены с пополнением"));
//        }

        return warnings;
    }

    private List<StateProblem> getProblems(WareHouse wareHouse) {
        List<StateProblem> problems = new ArrayList<>();

//        // Ошибка: Нет отчета о завершении рабочей смены с пополнением при наличии отчета о поступлении
//
//
//        // Ошибка: Нет отчета об реализации РМ при наличии отчета о поступлении РМ
//        List<Report> replenishmentReports = getAllReplenishmentReports(wareHouse);
//        List<Report> releaseReports = getAllReleaseReports(wareHouse);
//        if (!releaseReports.isEmpty() && replenishmentReports.isEmpty()) {
//            warnings.add(new StateWarning("Нет отчета о выпуске при наличии отчета о поступлении"));
//        }
//
//
//        // Проблема: Отрицательные значения в учете (невозможное состояние)
//        Map<ItemID, Integer> storageState = getStorageState(wareHouse);
//        Map<ItemID, Integer> invalidStorageStatePositions =
//                storageState
//                        .entrySet()
//                        .stream()
//                        .filter(e -> e.getValue() < 0)
//                        .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
//        problems.add(new InvalidStorageStateProblem(invalidStorageStatePositions));

        // Проблема: Данные инвентаризации не совпали с ожидаемыми
//        List<Report> inventarisationReports = getAllInventarisationReports(wareHouse, ReportSortCriteria.SortType.DESCENDING);
//        Iterator invIter = inventarisationReports.iterator();
//        if (invIter.hasNext()) {
//            InventarisationReport currInvRep = (InventarisationReport) invIter.next();
//            while (invIter.hasNext()) {
//                Map<ItemID, Integer> expectedStorageState = getStorageState(currInvRep.getWareHouseId());
//            }
//            for (Report inventarisationReport : inventarisationReports) {
//                Map<ItemID, Integer> expectedStorageState = getStorageState(inventarisationReport.getId());
//                Map<ItemID, Integer> actualStorageState = ((InventarisationReport) inventarisationReport).getItems();
//                problems.add(new InventarisationProblem());
//            }
//        }
        return problems;
    }
}

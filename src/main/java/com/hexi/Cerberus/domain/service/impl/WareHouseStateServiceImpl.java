package com.hexi.Cerberus.domain.service.impl;

import com.hexi.Cerberus.domain.helpers.ItemMapHelper;
import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.item.repository.ItemRepository;
import com.hexi.Cerberus.domain.report.Report;
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
import com.hexi.Cerberus.domain.warehouse.WareHouseState;
import com.hexi.Cerberus.infrastructure.StateProblem;
import com.hexi.Cerberus.infrastructure.StateWarning;
import com.hexi.Cerberus.infrastructure.query.Query;
import com.hexi.Cerberus.infrastructure.query.comparation.ComparationContainer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class WareHouseStateServiceImpl implements WareHouseStateService {
    public final ReportRepository reportRepository;
    public final ItemRepository itemRepository;

    public List<WareHouseReport> getReports(WareHouse wareHouse, Date treshold) {
        WareHouseReportFilterCriteria filter = WareHouseReportFilterCriteria
                .builder()
                .date(new ComparationContainer<>(treshold, ComparationContainer.Sign.GREATEREQUAL))
                .status(ReportStatus.ACTIVE)
                .department(wareHouse.getParentDepartment().getId())
                .warehouse(wareHouse.getId())
                .build();
        return (List<WareHouseReport>) reportRepository.findAllWithQuery(new Query(filter, null, null)).stream().map(report -> (WareHouseReport) report).collect(Collectors.toList());
    }

    public List<WareHouseReport> getReports(WareHouse wareHouse) {
        WareHouseReportFilterCriteria filter = WareHouseReportFilterCriteria
                .builder()
                .status(ReportStatus.ACTIVE)
                .department(wareHouse.getParentDepartment().getId())
                .warehouse(wareHouse.getId())
                .build();
        return (List<WareHouseReport>) reportRepository.findAllWithQuery(new Query(filter, null, null)).stream().map(report -> (WareHouseReport) report).collect(Collectors.toList());
    }

    public Map<ItemID, Integer> getStorageState(WareHouse wareHouse) {

        InventarisationReportFilterCriteria inventarisationReportFilter = InventarisationReportFilterCriteria
                .builder()
                .status(ReportStatus.ACTIVE)
                .department(wareHouse.getParentDepartment().getId())
                .warehouse(wareHouse.getId())
                .build();

        ReportSortCriteria inventarisationReportSort = ReportSortCriteria.builder().sortBy(ReportSortCriteria.SortBy.CREATED).sortType(ReportSortCriteria.SortType.DESCENDING).build();


        Optional<Report> inventarisationBase = reportRepository.findAllWithQuery(new Query(inventarisationReportFilter, inventarisationReportSort, null)).stream().findFirst();

        Map<ItemID, Integer> baseStorageState;
        Date dateTreshold;
        List<WareHouseReport> wareHouseReports;

        if (inventarisationBase.isPresent()) {
            baseStorageState =
                    ((InventarisationReport) inventarisationBase
                            .get())
                            .getItems()
                            .entrySet()
                            .stream()
                            .map(itemIntegerEntry -> new AbstractMap.SimpleImmutableEntry<ItemID, Integer>(itemIntegerEntry.getKey().getId(), itemIntegerEntry.getValue()))
                            .collect(Collectors.toMap(AbstractMap.SimpleImmutableEntry::getKey, AbstractMap.SimpleImmutableEntry::getValue, Integer::sum));
            dateTreshold = inventarisationBase.get().getCreatedAt();
            wareHouseReports = getReports(wareHouse, dateTreshold);
        } else {
            baseStorageState = new HashMap<>();
            dateTreshold = null;
            wareHouseReports = getReports(wareHouse);
        }


        Map<ItemID, Integer> itemReplenishes =
                wareHouseReports
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
        Map<ItemID, Integer> itemReleases =
                wareHouseReports
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

        return ItemMapHelper.MergeDictionariesWithSub(
                ItemMapHelper.MergeDictionariesWithSum(baseStorageState, itemReplenishes),
                itemReleases
        );

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
        //TODO


        return warnings;
    }

    private List<StateProblem> getProblems(WareHouse wareHouse) {
        List<StateProblem> problems = new ArrayList<>();
        //TODO

        return problems;
    }
}

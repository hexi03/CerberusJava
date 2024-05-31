package com.hexi.Cerberus.adapter.persistence.service;

import com.hexi.Cerberus.adapter.persistence.item.base.ItemEntry;
import com.hexi.Cerberus.adapter.persistence.item.base.ItemModel;
import com.hexi.Cerberus.adapter.persistence.product.base.ProductEntry;
import com.hexi.Cerberus.adapter.persistence.product.base.ProductModel;
import com.hexi.Cerberus.adapter.persistence.report.base.factorysite.SupplyRequirementReportModel;
import com.hexi.Cerberus.adapter.persistence.report.base.factorysite.WorkShiftReportModel;
import com.hexi.Cerberus.adapter.persistence.report.base.warehouse.ReleaseReportModel;
import com.hexi.Cerberus.adapter.persistence.report.base.warehouse.WorkShiftR11tReportModel;
import com.hexi.Cerberus.domain.helpers.ItemMapHelper;
import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.domain.report.factorysite.SupplyRequirementReport;
import com.hexi.Cerberus.domain.report.factorysite.WorkShiftReport;
import com.hexi.Cerberus.domain.report.repository.ReportRepository;
import com.hexi.Cerberus.domain.report.service.ReportQueryService;
import com.hexi.Cerberus.domain.report.warehouse.ReleaseReport;
import com.hexi.Cerberus.domain.warehouse.WareHouse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReportQueryServiceImpl implements ReportQueryService {
    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    ReportRepository reportRepository;

    @Override
    public List<AbstractMap.SimpleImmutableEntry<WorkShiftReport,Map<ItemID, Integer>>> findUnsatisfiedByReplenishedProducedWorkShiftReports(WareHouse targetWareHouse) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> wsrpQuery = cb.createTupleQuery();
        Root<WorkShiftReportModel> wsrpRoot = wsrpQuery.from(WorkShiftReportModel.class);
        Join<WorkShiftReportModel, ProductEntry> peJoin = wsrpRoot.join("produced");
        Join<ProductEntry, ProductModel> pJoin = peJoin.join("product");
        Join<ProductModel, ItemModel> iJoin = pJoin.join("producedItem");
        wsrpQuery.multiselect(
                wsrpRoot.get("id").alias("wsrepid"),
                iJoin.get("id").alias("item_id"),
                cb.sum(peJoin.get("amount")).alias("amount"));
        wsrpQuery.where(cb.equal(wsrpRoot.get("targetWareHouses").get("id"),targetWareHouse.getId()));
        wsrpQuery.groupBy(wsrpRoot.get("id"),iJoin.get("id"));

        CriteriaQuery<Tuple> wsr11trpQuery = cb.createTupleQuery();
        Root<WorkShiftR11tReportModel> wsr11trpRoot = wsr11trpQuery.from(WorkShiftR11tReportModel.class);
        Join<WorkShiftReportModel, ItemEntry> ieJoin = wsr11trpRoot.join("items");
        wsr11trpQuery.multiselect(
                wsr11trpRoot.get("workShiftReport").get("id").alias("wsrepid"),
                ieJoin.get("item").get("id").alias("item_id"),
                cb.sum(ieJoin.get("amount")).alias("amount"));
        wsr11trpQuery.where(cb.equal(wsr11trpRoot.get("wareHouse").get("id"),targetWareHouse.getId()));
        wsr11trpQuery.groupBy(wsr11trpRoot.get("workShiftReport").get("id"),ieJoin.get("item").get("id"));




        Map<ReportID, Map<ItemID, Integer>> wsrpie = entityManager.createQuery(wsrpQuery).getResultStream()
                .collect(
                        Collectors.toMap(
                                tuple -> (ReportID) tuple.get("wsrepid"),
                                tuple -> Map.ofEntries(new AbstractMap.SimpleEntry<ItemID, Integer>(
                                        (ItemID) tuple.get("item_id"),
                                        (Integer) tuple.get("amount")
                                )),
                                (map, map2) -> {map2.entrySet().stream().forEach(entry2 -> map.merge(entry2.getKey(), entry2.getValue(), Integer::sum)); return map;}
                        )
                );
        Map<ReportID, Map<ItemID, Integer>> wsr11trpie = entityManager.createQuery(wsr11trpQuery).getResultStream()
                .collect(
                        Collectors.toMap(
                                tuple -> (ReportID) tuple.get("wsrepid"),
                                tuple -> Map.ofEntries(new AbstractMap.SimpleEntry<ItemID, Integer>(
                                        (ItemID) tuple.get("item_id"),
                                        (Integer) tuple.get("amount")
                                )),
                                (map, map2) -> {map2.entrySet().stream().forEach(entry2 -> map.merge(entry2.getKey(), entry2.getValue(), Integer::sum)); return map;}
                                )
                );

        HashMap<ReportID,Map<ItemID, Integer>> wsReps = new HashMap<>(wsrpie);
        wsr11trpie.entrySet().forEach(wsr11rentry -> wsReps.merge(wsr11rentry.getKey(), wsr11rentry.getValue(), (map, map2) -> ItemMapHelper.filterNonZero(ItemMapHelper.MergeDictionariesWithSub(map,map2))));

        Map<ReportID,WorkShiftReport> reps =
                ((List<WorkShiftReport>)reportRepository
                        .findAllById(wsReps.keySet())
                ).stream()
                        .collect(Collectors.toMap(o -> o.getId(), o -> o));
        return wsReps.entrySet().stream().map(entry -> new AbstractMap.SimpleImmutableEntry<WorkShiftReport,Map<ItemID, Integer>>(reps.get(entry.getKey()), entry.getValue())).collect(Collectors.toList());

    }
    @Override
    public List<AbstractMap.SimpleImmutableEntry<WorkShiftReport,Map<ItemID, Integer>>> findUnsatisfiedByReplenishedUnclaimedRemainsWorkShiftReports(WareHouse targetWareHouse) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> wsrpQuery = cb.createTupleQuery();
        Root<WorkShiftReportModel> wsrpRoot = wsrpQuery.from(WorkShiftReportModel.class);
        Join<WorkShiftReportModel, ItemEntry> ureJoin = wsrpRoot.join("unclaimedRemains");
        wsrpQuery.multiselect(
                wsrpRoot.get("id").alias("wsrepid"),
                ureJoin.get("item").get("id").alias("item_id"),
                cb.sum(ureJoin.get("amount")).alias("amount"));
        wsrpQuery.where(cb.equal(wsrpRoot.get("targetWareHouses").get("id"),targetWareHouse.getId()));
        wsrpQuery.groupBy(wsrpRoot.get("id"),ureJoin.get("item").get("id"));

        CriteriaQuery<Tuple> wsr11trpQuery = cb.createTupleQuery();
        Root<WorkShiftR11tReportModel> wsr11trpRoot = wsr11trpQuery.from(WorkShiftR11tReportModel.class);
        Join<WorkShiftReportModel, ItemEntry> ieJoin = wsr11trpRoot.join("unclaimedRemains");
        wsr11trpQuery.multiselect(
                wsr11trpRoot.get("workShiftReport").get("id").alias("wsrepid"),
                ieJoin.get("item").get("id").alias("item_id"),
                cb.sum(ieJoin.get("amount")).alias("amount"));
        wsr11trpQuery.where(cb.equal(wsr11trpRoot.get("wareHouse").get("id"),targetWareHouse.getId()));
        wsr11trpQuery.groupBy(wsr11trpRoot.get("workShiftReport").get("id"),ieJoin.get("item").get("id"));




        Map<ReportID, Map<ItemID, Integer>> wsrpie = entityManager.createQuery(wsrpQuery).getResultStream()
                .collect(
                        Collectors.toMap(
                                tuple -> (ReportID) tuple.get("wsrepid"),
                                tuple -> Map.ofEntries(new AbstractMap.SimpleEntry<ItemID, Integer>(
                                        (ItemID) tuple.get("item_id"),
                                        (Integer) tuple.get("amount")
                                )),
                                (map, map2) -> {map2.entrySet().stream().forEach(entry2 -> map.merge(entry2.getKey(), entry2.getValue(), Integer::sum)); return map;}
                        )
                );
        Map<ReportID, Map<ItemID, Integer>> wsr11trpie = entityManager.createQuery(wsr11trpQuery).getResultStream()
                .collect(
                        Collectors.toMap(
                                tuple -> (ReportID) tuple.get("wsrepid"),
                                tuple -> Map.ofEntries(new AbstractMap.SimpleEntry<ItemID, Integer>(
                                        (ItemID) tuple.get("item_id"),
                                        (Integer) tuple.get("amount")
                                )),
                                (map, map2) -> {map2.entrySet().stream().forEach(entry2 -> map.merge(entry2.getKey(), entry2.getValue(), Integer::sum)); return map;}
                        )
                );

        HashMap<ReportID,Map<ItemID, Integer>> wsReps = new HashMap<>(wsrpie);
        wsr11trpie.entrySet().forEach(wsr11rentry -> wsReps.merge(wsr11rentry.getKey(), wsr11rentry.getValue(), (map, map2) -> ItemMapHelper.filterNonZero(ItemMapHelper.MergeDictionariesWithSub(map,map2))));
        Map<ReportID,WorkShiftReport> reps =
                ((List<WorkShiftReport>)reportRepository
                        .findAllById(wsReps.keySet())
                        ).stream()
                        .collect(Collectors.toMap(o -> o.getId(), o -> o));
        return wsReps.entrySet().stream().map(entry -> new AbstractMap.SimpleImmutableEntry<WorkShiftReport,Map<ItemID, Integer>>(reps.get(entry.getKey()), entry.getValue())).collect(Collectors.toList());

    }

    @Override
    public List<AbstractMap.SimpleImmutableEntry<SupplyRequirementReport,Map<ItemID, Integer>>> findUnsatisfiedSupplyRequirementReports(WareHouse targetWareHouse) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> srrQuery = cb.createTupleQuery();
        Root<SupplyRequirementReportModel> srrRoot = srrQuery.from(SupplyRequirementReportModel.class);
        Join<SupplyRequirementReportModel, ItemEntry> ureJoin = srrRoot.join("requirements");
        srrQuery.multiselect(
                srrRoot.get("id").alias("srrid"),
                ureJoin.get("item").get("id").alias("item_id"),
                cb.sum(ureJoin.get("amount")).alias("amount"));
        srrQuery.where(cb.equal(srrRoot.get("targetWareHouses").get("id"),targetWareHouse.getId()));
        srrQuery.groupBy(srrRoot.get("id"),ureJoin.get("item").get("id"));

        CriteriaQuery<Tuple> rrQuery = cb.createTupleQuery();
        Root<ReleaseReportModel> rrRoot = rrQuery.from(ReleaseReportModel.class);
        Join<ReleaseReportModel, ItemEntry> ieJoin = rrRoot.join("items");
        rrQuery.multiselect(
                rrRoot.get("supplyReqReport").get("id").alias("srrid"),
                ieJoin.get("item").get("id").alias("item_id"),
                cb.sum(ieJoin.get("amount")).alias("amount"));
        rrQuery.where(cb.equal(rrRoot.get("wareHouse").get("id"),targetWareHouse.getId()));
        rrQuery.groupBy(rrRoot.get("supplyReqReport").get("id"),ieJoin.get("item").get("id"));




        Map<ReportID, Map<ItemID, Integer>> srrie = entityManager.createQuery(srrQuery).getResultStream()
                .collect(
                        Collectors.toMap(
                                tuple -> (ReportID) tuple.get("srrid"),
                                tuple -> Map.ofEntries(new AbstractMap.SimpleEntry<ItemID, Integer>(
                                        (ItemID) tuple.get("item_id"),
                                        (Integer) tuple.get("amount")
                                )),
                                (map, map2) -> {map2.entrySet().stream().forEach(entry2 -> map.merge(entry2.getKey(), entry2.getValue(), Integer::sum)); return map;}
                        )
                );
        Map<ReportID, Map<ItemID, Integer>> rrie = entityManager.createQuery(rrQuery).getResultStream()
                .collect(
                        Collectors.toMap(
                                tuple -> (ReportID) tuple.get("srrid"),
                                tuple -> Map.ofEntries(new AbstractMap.SimpleEntry<ItemID, Integer>(
                                        (ItemID) tuple.get("item_id"),
                                        (Integer) tuple.get("amount")
                                )),
                                (map, map2) -> {map2.entrySet().stream().forEach(entry2 -> map.merge(entry2.getKey(), entry2.getValue(), Integer::sum)); return map;}
                        )
                );

        HashMap<ReportID,Map<ItemID, Integer>> wsReps = new HashMap<>(srrie);
        rrie.entrySet().forEach(wsr11rentry -> wsReps.merge(wsr11rentry.getKey(), wsr11rentry.getValue(), (map, map2) -> ItemMapHelper.filterNonZero(ItemMapHelper.MergeDictionariesWithSub(map,map2))));
        Map<ReportID,SupplyRequirementReport> reps =
                ((List<SupplyRequirementReport>)reportRepository
                        .findAllById(wsReps.keySet())
                ).stream()
                        .collect(Collectors.toMap(o -> o.getId(), o -> o));
        return wsReps.entrySet().stream().map(entry -> new AbstractMap.SimpleImmutableEntry<SupplyRequirementReport ,Map<ItemID, Integer>>(reps.get(entry.getKey()), entry.getValue())).collect(Collectors.toList());

    }


}

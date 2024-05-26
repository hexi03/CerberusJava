package com.hexi.Cerberus.adapter.persistence.service;


import com.hexi.Cerberus.adapter.persistence.item.base.ItemEntry;
import com.hexi.Cerberus.adapter.persistence.product.base.ProductEntry;
import com.hexi.Cerberus.adapter.persistence.product.base.ProductModel;
import com.hexi.Cerberus.adapter.persistence.report.base.factorysite.WorkShiftReportModel;
import com.hexi.Cerberus.adapter.persistence.report.base.warehouse.ReleaseReportModel;
import com.hexi.Cerberus.domain.factorysite.FactorySite;
import com.hexi.Cerberus.domain.helpers.ItemMapHelper;
import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.item.service.ItemRegistriesQueryService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class ItemRegistriesQueryServiceImpl implements ItemRegistriesQueryService {
    @PersistenceContext
    EntityManager entityManager;
    @Override
    public Map<ItemID, Integer> getFactorySiteLostedOnSiteConsumables(FactorySite factorySite) {


//        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//        // Создаем CriteriaQuery
//        JpaCriteriaQuery<Tuple> mainQuery = (JpaCriteriaQuery<Tuple>) criteriaBuilder.createTupleQuery();
//        Root<ItemModel> itemRegistryRoot = mainQuery.from(ItemModel.class);
//
//// Подзапрос для вычисления wsrp
//        JpaSubQuery<Tuple> wsrpSubquery = mainQuery.subquery(Tuple.class);
//        Root<WorkShiftReportModel> repRoot1 = wsrpSubquery.from(WorkShiftReportModel.class);
//        Join<WorkShiftReportModel, Product> peJoin1 = repRoot1.join("produced");
//        Join<Product, ProductModel> prJoin1 = peJoin1.join("product");
//        Join<ProductModel, Item> ieJoin1 = prJoin1.join("requirements");
//        Join<Item, ItemModel> irJoin1 = ieJoin1.join("item");
//        wsrpSubquery.multiselect(irJoin1.get("id"), criteriaBuilder.sum(criteriaBuilder.prod(ieJoin1.get("amount"), peJoin1.get("amount"))));
//        wsrpSubquery.groupBy(irJoin1.get("id"));
//
//// Подзапрос для вычисления wsrlrie
//        JpaSubQuery<Tuple> wsrlieSubquery = mainQuery.subquery(Tuple.class);
//        Root<WorkShiftReportModel> repRoot2_1 = wsrlieSubquery.from(WorkShiftReportModel.class);
//        Join<WorkShiftReportModel, Item> ieJoin2_1 = repRoot2_1.join("losses");
//        wsrlieSubquery.multiselect(ieJoin2_1.get("item").get("id"), criteriaBuilder.sum(ieJoin2_1.get("amount")));
//        wsrlieSubquery.groupBy(ieJoin2_1.get("item").get("id"));
//
//        JpaSubQuery<Tuple> wsrrieSubquery = mainQuery.subquery(Tuple.class);
//        Root<WorkShiftReportModel> repRoot2_2 = wsrrieSubquery.from(WorkShiftReportModel.class);
//        Join<WorkShiftReportModel, Item> ieJoin2_2 = repRoot2_2.join("remains");
//        wsrrieSubquery.multiselect(ieJoin2_2.get("item").get("id"), criteriaBuilder.sum(ieJoin2_2.get("amount")));
//        wsrrieSubquery.groupBy(ieJoin2_2.get("item").get("id"));
//
//
//        JpaSubQuery<Tuple> wsrlrieSubquery = mainQuery.subquery(Tuple.class);
//        wsrlrieSubquery.select(criteriaBuilder.function("union", Tuple.class, wsrlieSubquery.getSelection(), wsrrieSubquery.getSelection()));
//
//
//// Подзапрос для вычисления rrie
//        JpaSubQuery<Tuple> rrieSubquery = mainQuery.subquery(Tuple.class);
//        Root<ReleaseReportModel> repRoot3 = rrieSubquery.from(ReleaseReportModel.class);
//        Join<ReleaseReportModel, Item> ieJoin3 = repRoot3.join("item");
//        rrieSubquery.multiselect(ieJoin3.get("item").get("id"), criteriaBuilder.sum(ieJoin3.get("amount")));
//        rrieSubquery.groupBy(ieJoin3.get("item").get("id"));
//
//// Создаем основное выражение для вычисления разницы
//        Expression<Integer> differenceExpression = criteriaBuilder.diff(
//                criteriaBuilder.diff(
//                        criteriaBuilder.coalesce(rrieSubquery.correlate(itemRegistryRoot).get("amount"), 0),
//                        criteriaBuilder.coalesce(wsrlrieSubquery.correlate(itemRegistryRoot).get("amount"), 0)
//                ),
//                criteriaBuilder.coalesce(wsrpSubquery.correlate(itemRegistryRoot).get("amount"), 0)
//        );
//// Формируем основной запрос
//        mainQuery.multiselect(
//                itemRegistryRoot,
//                differenceExpression.alias("difference")
//        );
//        mainQuery.where(
//                criteriaBuilder.or(
//                        criteriaBuilder.equal(itemRegistryRoot.get("id"), wsrpSubquery.correlate(itemRegistryRoot).get("item_id")),
//                        criteriaBuilder.equal(itemRegistryRoot.get("id"), wsrlrieSubquery.correlate(itemRegistryRoot).get("item_id")),
//                        criteriaBuilder.equal(itemRegistryRoot.get("id"), rrieSubquery.correlate(itemRegistryRoot).get("item_id"))
//                )
//        );
//



        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> wsrpQuery = cb.createTupleQuery();
        Root<WorkShiftReportModel> wsrpRoot = wsrpQuery.from(WorkShiftReportModel.class);
        Join<WorkShiftReportModel, ProductEntry> peJoin = wsrpRoot.join("produced");
        Join<ProductEntry, ProductModel> pJoin = peJoin.join("product");
        Join<ProductModel, ItemEntry> ieJoin = pJoin.join("requirements");
        wsrpQuery.multiselect(ieJoin.get("item").get("id").alias("item_id"),
                cb.sum(cb.prod(ieJoin.get("amount"), peJoin.get("amount"))).alias("amount"));
        wsrpQuery.groupBy(ieJoin.get("item").get("id"));
        wsrpQuery.where(cb.equal(wsrpRoot.get("factorySite").get("id"), factorySite.getId()));
        wsrpQuery.where(cb.isNull(wsrpRoot.get("deletedAt")));

// Criteria query for wsrlieReq
        CriteriaQuery<Tuple> wsrlieQuery = cb.createTupleQuery();
        Root<WorkShiftReportModel> wsrlieRoot = wsrlieQuery.from(WorkShiftReportModel.class);
        Join<WorkShiftReportModel, ItemEntry> ieJoin1 = wsrlieRoot.join("losses");
        wsrlieQuery.multiselect(ieJoin1.get("item").get("id").alias("item_id"),
                cb.sum(ieJoin1.get("amount")).alias("amount"));
        wsrlieQuery.groupBy(ieJoin1.get("item").get("id"));
        wsrlieQuery.where(cb.equal(wsrlieRoot.get("factorySite").get("id"), factorySite.getId()));
        wsrlieQuery.where(cb.isNull(wsrlieRoot.get("deletedAt")));

// Criteria query for wsrrieReq
        CriteriaQuery<Tuple> wsrrieQuery = cb.createTupleQuery();
        Root<WorkShiftReportModel> wsrrieRoot = wsrrieQuery.from(WorkShiftReportModel.class);
        Join<WorkShiftReportModel, ItemEntry> ieJoin2 = wsrrieRoot.join("remains");
        wsrrieQuery.multiselect(ieJoin2.get("item").get("id").alias("item_id"),
                cb.sum(ieJoin2.get("amount")).alias("amount"));
        wsrrieQuery.groupBy(ieJoin2.get("item").get("id"));
        wsrrieQuery.where(cb.equal(wsrrieRoot.get("factorySite").get("id"), factorySite.getId()));
        wsrrieQuery.where(cb.isNull(wsrrieRoot.get("deletedAt")));

        CriteriaQuery<Tuple> wsrurieQuery = cb.createTupleQuery();
        Root<WorkShiftReportModel> wsrurieRoot = wsrurieQuery.from(WorkShiftReportModel.class);
        Join<WorkShiftReportModel, ItemEntry> ieJoin2_1 = wsrurieRoot.join("unclaimedRemains");
        wsrurieQuery.multiselect(ieJoin2_1.get("item").get("id").alias("item_id"),
                cb.sum(ieJoin2_1.get("amount")).alias("amount"));
        wsrurieQuery.groupBy(ieJoin2_1.get("item").get("id"));
        wsrurieQuery.where(cb.equal(wsrurieRoot.get("factorySite").get("id"), factorySite.getId()));
        wsrurieQuery.where(cb.isNull(wsrurieRoot.get("deletedAt")));
// Criteria query for rrieReq
        CriteriaQuery<Tuple> rrieQuery = cb.createTupleQuery();
        Root<ReleaseReportModel> rrieRoot = rrieQuery.from(ReleaseReportModel.class);
        Join<ReleaseReportModel, Item> ieJoin3 = rrieRoot.join("items");
        rrieQuery.multiselect(ieJoin3.get("item").get("id").alias("item_id"),
                cb.sum(ieJoin3.get("amount")).alias("amount"));
        rrieQuery.groupBy(ieJoin3.get("item").get("id"));
        rrieQuery.where(cb.equal(rrieRoot.get("supplyReqReport").get("factorySite").get("id"), factorySite.getId()));
        rrieQuery.where(cb.isNull(rrieRoot.get("deletedAt")));

// Execute queries and collect results
        Map<ItemID, Integer> wsrpie = entityManager.createQuery(wsrpQuery).getResultStream()
                .collect(Collectors.toMap(tuple -> (ItemID) tuple.get("item_id"), tuple -> (Integer) tuple.get("amount")));
        Map<ItemID, Integer> wsrlie = entityManager.createQuery(wsrlieQuery).getResultStream()
                .collect(Collectors.toMap(tuple -> (ItemID) tuple.get("item_id"), tuple -> (Integer) tuple.get("amount")));
        Map<ItemID, Integer> wsrrie = entityManager.createQuery(wsrrieQuery).getResultStream()
                .collect(Collectors.toMap(tuple -> (ItemID) tuple.get("item_id"), tuple -> (Integer) tuple.get("amount")));
        Map<ItemID, Integer> wsrurie = entityManager.createQuery(wsrurieQuery).getResultStream()
                .collect(Collectors.toMap(tuple -> (ItemID) tuple.get("item_id"), tuple -> (Integer) tuple.get("amount")));
        Map<ItemID, Integer> rrie = entityManager.createQuery(rrieQuery).getResultStream()
                .collect(Collectors.toMap(tuple -> (ItemID) tuple.get("item_id"), tuple -> (Integer) tuple.get("amount")));
        System.out.println("wsrpie: " + wsrpie);
        System.out.println("wsrlie: " + wsrlie);
        System.out.println("wsrrie: " + wsrrie);
        System.out.println("wsrurie: " + wsrurie);
        System.out.println("rrie: " + rrie);

// Perform required merging operations
        Map<ItemID, Integer> result = ItemMapHelper.filterNonZero(ItemMapHelper.MergeDictionariesWithSub(
                rrie,
                ItemMapHelper.MergeDictionariesWithSum(
                        wsrpie,
                        ItemMapHelper.MergeDictionariesWithSum(wsrurie,ItemMapHelper.MergeDictionariesWithSum(wsrrie, wsrlie))
                )
        ));
        System.out.println("result: " + result);


        return result;

    }

//    @SuppressWarnings("unchecked")
//    private <E> Predicate isMember(
//            CriteriaBuilder cb,
//            Object a,
//            Object b
//    ) {
//
//        return cb.isMember((Expression<E>) a, (Expression<? extends Collection<E>>) b);
//    }
}

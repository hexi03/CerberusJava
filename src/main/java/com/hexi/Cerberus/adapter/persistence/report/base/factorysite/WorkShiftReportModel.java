package com.hexi.Cerberus.adapter.persistence.report.base.factorysite;

import com.hexi.Cerberus.adapter.persistence.factorysite.base.FactorySiteModel;
import com.hexi.Cerberus.adapter.persistence.item.base.ItemModel;
import com.hexi.Cerberus.adapter.persistence.product.base.ProductModel;
import com.hexi.Cerberus.adapter.persistence.warehouse.base.WareHouseModel;
import com.hexi.Cerberus.domain.factorysite.FactorySite;
import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.product.Product;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.domain.report.factorysite.WorkShiftReport;
import com.hexi.Cerberus.domain.warehouse.WareHouse;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
@Access(AccessType.FIELD)
public class WorkShiftReportModel extends FactorySiteReportModel implements WorkShiftReport {
    WareHouseModel targetWareHouseId;
    Map<ProductModel, Integer> produced = new HashMap<>();
    Map<ItemModel, Integer> losses = new HashMap<>();
    Map<ItemModel, Integer> remains = new HashMap<>();

    public WorkShiftReportModel(
            ReportID id,
            FactorySiteModel factorySite,
            Date createdAt,
            Date expirationDate,
            Optional<Date> deletedAt,
            WareHouseModel targetWareHouseId,
            Map<ProductModel, Integer> produced,
            Map<ItemModel, Integer> losses,
            Map<ItemModel, Integer> remains) {
        super(id, factorySite, createdAt, expirationDate, deletedAt);
        this.targetWareHouseId = targetWareHouseId;
        this.produced = produced;
        this.losses = losses;
        this.remains = remains;
    }

    public WorkShiftReportModel(
            ReportID id,
            FactorySiteModel factorySite,
            Date createdAt,
            Date expirationDate,
            WareHouseModel targetWareHouseId,
            Map<ProductModel, Integer> produced,
            Map<ItemModel, Integer> losses,
            Map<ItemModel, Integer> remains) {
        super(id, factorySite, createdAt, expirationDate);
        this.targetWareHouseId = targetWareHouseId;
        this.produced = produced;
        this.losses = losses;
        this.remains = remains;
    }

    private WorkShiftReportModel() {
        super();
    }

    @Override
    public void setTargetWareHouse(WareHouse wareHouse) {
        this.targetWareHouseId = (WareHouseModel) wareHouse;
    }

    @Override
    public Map<Product, Integer> getProduced() {
        return produced
                .entrySet()
                .stream()
                .collect(
                        Collectors.toMap(
                                entry -> entry.getKey(),
                                entry -> entry.getValue()
                        )
                );
    }

    @Override
    public void setProduced(Map<Product, Integer> producedMap) {
        this.produced = producedMap
                .entrySet()
                .stream()
                .collect(Collectors.toMap(entry -> (ProductModel) entry.getKey(), entry -> entry.getValue()));
    }

    @Override
    public Map<Item, Integer> getRemains() {
        return remains
                .entrySet()
                .stream()
                .collect(
                        Collectors.toMap(
                                entry -> entry.getKey(),
                                entry -> entry.getValue()
                        )
                );
    }

    @Override
    public void setRemains(Map<Item, Integer> remainsMap) {
        this.remains = remainsMap
                .entrySet()
                .stream()
                .collect(Collectors.toMap(entry -> (ItemModel) entry.getKey(), entry -> entry.getValue()));
    }

    @Override
    public Map<Item, Integer> getLosses() {
        return losses
                .entrySet()
                .stream()
                .collect(
                        Collectors.toMap(
                                entry -> entry.getKey(),
                                entry -> entry.getValue()
                        )
                );
    }

    @Override
    public void setLosses(Map<Item, Integer> lossesMap) {
        this.losses = lossesMap
                .entrySet()
                .stream()
                .collect(Collectors.toMap(entry -> (ItemModel) entry.getKey(), entry -> entry.getValue()));
    }
}

package com.hexi.Cerberus.adapter.persistence.report.base.factorysite;

import com.hexi.Cerberus.adapter.persistence.factorysite.base.FactorySiteModel;
import com.hexi.Cerberus.adapter.persistence.item.base.ItemEntry;
import com.hexi.Cerberus.adapter.persistence.item.base.ItemModel;
import com.hexi.Cerberus.adapter.persistence.product.base.ProductEntry;
import com.hexi.Cerberus.adapter.persistence.product.base.ProductModel;
import com.hexi.Cerberus.adapter.persistence.warehouse.base.WareHouseModel;
import com.hexi.Cerberus.domain.factorysite.FactorySite;
import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.product.Product;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.domain.report.factorysite.WorkShiftReport;
import com.hexi.Cerberus.domain.warehouse.WareHouse;
import jakarta.persistence.*;

import java.util.*;
import java.util.stream.Collectors;
@Entity
@Access(AccessType.FIELD)
public class WorkShiftReportModel extends FactorySiteReportModel implements WorkShiftReport {
    @ManyToOne(cascade = CascadeType.ALL)
    WareHouseModel targetWareHouseId;
    @OneToMany(cascade = CascadeType.ALL)
    Collection<ProductEntry> produced = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL)
    Collection<ItemEntry> losses = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL)
    Collection<ItemEntry> remains = new ArrayList<>();

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
        this.produced = produced.entrySet().stream().map(entry -> new ProductEntry(entry.getKey(),entry.getValue())).collect(Collectors.toList());
        this.losses = losses.entrySet().stream().map(entry -> new ItemEntry(entry.getKey(),entry.getValue())).collect(Collectors.toList());
        this.remains = remains.entrySet().stream().map(entry -> new ItemEntry(entry.getKey(),entry.getValue())).collect(Collectors.toList());
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
        this.produced = produced.entrySet().stream().map(entry -> new ProductEntry(entry.getKey(),entry.getValue())).collect(Collectors.toList());
        this.losses = losses.entrySet().stream().map(entry -> new ItemEntry(entry.getKey(),entry.getValue())).collect(Collectors.toList());
        this.remains = remains.entrySet().stream().map(entry -> new ItemEntry(entry.getKey(),entry.getValue())).collect(Collectors.toList());

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
                .stream()
                .collect(
                        Collectors.toMap(
                                entry -> entry.getProduct(),
                                entry -> entry.getAmount()
                        )
                );
    }

    @Override
    public void setProduced(Map<Product, Integer> producedMap) {
        this.produced = producedMap
                .entrySet()
                .stream().map(entry -> new ProductEntry((ProductModel) entry.getKey(),entry.getValue())).collect(Collectors.toList());
    }

    @Override
    public Map<Item, Integer> getRemains() {
        return remains
                .stream()
                .collect(
                        Collectors.toMap(
                                entry -> entry.getItem(),
                                entry -> entry.getAmount()
                        )
                );
    }

    @Override
    public void setRemains(Map<Item, Integer> remainsMap) {
        this.remains = remainsMap
                .entrySet()
                .stream().map(entry -> new ItemEntry((ItemModel) entry.getKey(),entry.getValue())).collect(Collectors.toList());
    }

    @Override
    public Map<Item, Integer> getLosses() {
        return losses
                .stream()
                .collect(
                        Collectors.toMap(
                                entry -> entry.getItem(),
                                entry -> entry.getAmount()
                        )
                );
    }

    @Override
    public void setLosses(Map<Item, Integer> lossesMap) {
        this.losses = lossesMap
                .entrySet()
                .stream().map(entry -> new ItemEntry((ItemModel) entry.getKey(),entry.getValue())).collect(Collectors.toList());
    }
}

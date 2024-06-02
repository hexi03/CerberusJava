package com.hexi.Cerberus.adapter.persistence.report.base.factorysite;

import com.hexi.Cerberus.adapter.persistence.factorysite.base.FactorySiteModel;
import com.hexi.Cerberus.adapter.persistence.item.base.ItemEntry;
import com.hexi.Cerberus.adapter.persistence.item.base.ItemModel;
import com.hexi.Cerberus.adapter.persistence.product.base.ProductEntry;
import com.hexi.Cerberus.adapter.persistence.product.base.ProductModel;
import com.hexi.Cerberus.adapter.persistence.user.base.UserModel;
import com.hexi.Cerberus.adapter.persistence.warehouse.base.WareHouseModel;
import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.product.Product;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.domain.report.factorysite.WorkShiftReport;
import com.hexi.Cerberus.domain.warehouse.WareHouse;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.*;
import java.util.stream.Collectors;
@Entity
@Access(AccessType.FIELD)

public class WorkShiftReportModel extends FactorySiteReportModel implements WorkShiftReport {
    @ManyToMany(cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinTable(name = "work_shift_report_target_warehouse_assoc")
    List<WareHouseModel> targetWareHouses;
    @ManyToMany(cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinTable(name = "work_shift_report_produced_product_entry_assoc")
    Collection<ProductEntry> produced = new ArrayList<>();
    @JoinTable(name = "work_shift_report_losses_item_entry_assoc")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToMany(cascade = CascadeType.ALL)
    Collection<ItemEntry> losses = new ArrayList<>();
    @JoinTable(name = "work_shift_report_remains_item_entry_assoc")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToMany(cascade = CascadeType.ALL)
    Collection<ItemEntry> remains = new ArrayList<>();
    @JoinTable(name = "work_shift_report_unclaimed_remains_item_entry_assoc")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToMany(cascade = CascadeType.ALL)
    Collection<ItemEntry> unclaimedRemains = new ArrayList<>();

    public WorkShiftReportModel(
            ReportID id,
            FactorySiteModel factorySite,
            Date createdAt,
            Date expirationDate,
            Optional<Date> deletedAt,
            List<WareHouseModel> targetWareHouses,
            Map<ProductModel, Integer> produced,
            Map<ItemModel, Integer> losses,
            Map<ItemModel, Integer> remains,
            Map<ItemModel, Integer> unclaimedRemains,
            UserModel creator) {
        super(id, factorySite, createdAt, expirationDate, deletedAt, creator);
        this.targetWareHouses = targetWareHouses;
        this.produced = produced.entrySet().stream().map(entry -> new ProductEntry(entry.getKey(),entry.getValue())).collect(Collectors.toList());
        this.losses = losses.entrySet().stream().map(entry -> new ItemEntry(entry.getKey(),entry.getValue())).collect(Collectors.toList());
        this.remains = remains.entrySet().stream().map(entry -> new ItemEntry(entry.getKey(),entry.getValue())).collect(Collectors.toList());
        this.unclaimedRemains = unclaimedRemains.entrySet().stream().map(entry -> new ItemEntry(entry.getKey(),entry.getValue())).collect(Collectors.toList());
    }

    public WorkShiftReportModel(
            ReportID id,
            FactorySiteModel factorySite,
            Date createdAt,
            Date expirationDate,
            List<WareHouseModel> targetWareHouses,
            Map<ProductModel, Integer> produced,
            Map<ItemModel, Integer> losses,
            Map<ItemModel, Integer> remains,
            Map<ItemModel, Integer> unclaimedRemains,
            UserModel creator) {
        super(id, factorySite, createdAt, expirationDate, creator);
        this.targetWareHouses = targetWareHouses;
        this.produced = produced.entrySet().stream().map(entry -> new ProductEntry(entry.getKey(),entry.getValue())).collect(Collectors.toList());
        this.losses = losses.entrySet().stream().map(entry -> new ItemEntry(entry.getKey(),entry.getValue())).collect(Collectors.toList());
        this.remains = remains.entrySet().stream().map(entry -> new ItemEntry(entry.getKey(),entry.getValue())).collect(Collectors.toList());
        this.unclaimedRemains = unclaimedRemains.entrySet().stream().map(entry -> new ItemEntry(entry.getKey(),entry.getValue())).collect(Collectors.toList());

    }

    private WorkShiftReportModel() {
        super();
    }

    @Override
    public void setTargetWareHouses(List<WareHouse> wareHouses) {
        this.targetWareHouses =  wareHouses.stream().map(wareHouse -> (WareHouseModel) wareHouse).collect(Collectors.toList());
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
    public Map<Item, Integer> getUnclaimedRemains() {
        return unclaimedRemains
                .stream()
                .collect(
                        Collectors.toMap(
                                entry -> entry.getItem(),
                                entry -> entry.getAmount()
                        )
                );
    }

    @Override
    public void setUnclaimedRemains(Map<Item, Integer> remainsMap) {
        this.unclaimedRemains = remainsMap
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

    @Override
    public List<WareHouse> getTargetWareHouses() {
        return targetWareHouses.stream().map(wareHouseModel -> (WareHouse) wareHouseModel).collect(Collectors.toList());
    }
}

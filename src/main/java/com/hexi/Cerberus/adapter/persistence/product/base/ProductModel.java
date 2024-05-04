package com.hexi.Cerberus.adapter.persistence.product.base;

import com.hexi.Cerberus.adapter.persistence.item.base.ItemEntry;
import com.hexi.Cerberus.adapter.persistence.item.base.ItemModel;
import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.product.Product;
import com.hexi.Cerberus.domain.product.ProductID;
import jakarta.persistence.*;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "product_registry")
@Access(AccessType.FIELD)
public class ProductModel extends Product {
    @EmbeddedId
    ProductID id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "production_item_id" )
    ItemModel producedItem;
    @OneToMany(cascade = CascadeType.ALL)
    List<ItemEntry> requirements;
    Date deletedAt;

    @Deprecated
    private ProductModel() {
        super();
    }

    public ProductModel(ProductID productID, Item item, Map<ItemModel, Integer> requirements) {
        this.id = new ProductID(productID);
        this.producedItem = (ItemModel) item;
        this.requirements = requirements.entrySet().stream().map(entry -> new ItemEntry(entry.getKey(),entry.getValue())).collect(Collectors.toList());
    }

    @Override
    public ProductID getId() {
        return new ProductID(id);
    }

    @Override
    public Item getProduction() {
        return producedItem;
    }

    @Override
    public void setProduction(Item production) {
        producedItem = (ItemModel) production;
    }

    @Override
    public Map<Item, Integer> getRequirements() {
        return requirements.stream().collect(Collectors.toMap(entry -> (Item)entry.getItem(), entry -> entry.getAmount()));
    }

    @Override
    public void setRequirements(Map<Item, Integer> requirements) {
        this.requirements = requirements.entrySet().stream().map(entry -> new ItemEntry((ItemModel) entry.getKey(),entry.getValue())).collect(Collectors.toList());

    }

    @Override
    public Optional<Date> getDeletedAt() {
        return Optional.ofNullable(deletedAt);
    }

}

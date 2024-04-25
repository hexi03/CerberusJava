package com.hexi.Cerberus.adapter.persistence.product.base;

import com.hexi.Cerberus.adapter.persistence.item.base.ItemModel;
import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.product.Product;
import com.hexi.Cerberus.domain.product.ProductID;
import jakarta.persistence.*;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "product_registry")

public class ProductModel extends Product {
    @EmbeddedId
    ProductID id;
    @ManyToOne
    @JoinColumn(name = "production_item_id")
    ItemModel producedItem;
    @ManyToMany
    @JoinTable(name = "product_required_item_assoc",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "requirement_item_id"))
    List<ItemModel> requirements;
    Date deletedAt;

    @Deprecated
    public ProductModel() {
        super();
    }

    public ProductModel(ProductID productID, Item item, List<ItemModel> requirements) {
        this.id = new ProductID(productID);
        this.producedItem = (ItemModel) item;
        this.requirements = requirements.stream().map(item1 -> (ItemModel) item1).toList();
    }

    @Override
    public ProductID getId() {
        return new ProductID(id);
    }

    @Override
    public Item getProduction() {
        return producedItem;
    }

    ;

    @Override
    public void setProduction(Item production) {
        producedItem = (ItemModel) production;
    }

    ;

    @Override
    public Collection<Item> getRequirements() {
        return requirements.stream().map(itemModel -> (Item) itemModel).collect(Collectors.toSet());
    }

    ;

    @Override
    public void setRequirements(List<Item> requirements) {
        this.requirements = requirements.stream().map(itemModel -> (ItemModel) itemModel).collect(Collectors.toList());

    }

    @Override
    public Optional<Date> getDeletedAt() {
        return Optional.ofNullable(deletedAt);
    }

}

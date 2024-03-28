package com.hexi.Cerberus.adapter.persistence.product.model;

import com.hexi.Cerberus.adapter.persistence.item.model.ItemModel;
import com.hexi.Cerberus.domain.group.GroupID;
import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.product.Product;
import com.hexi.Cerberus.domain.product.ProductID;
import com.hexi.Cerberus.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "product_registry")

public class ProductModel extends Product {
    @Id
    UUID id;
    @ManyToOne
    @JoinColumn(name = "production_item_id")
    ItemModel producedItem;
    @ManyToMany
    @JoinTable(name = "product_required_item_assoc",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "requirement_item_id"))
    List<ItemModel> requirements;
    Optional<Date> deletedAt;

    @Deprecated
    protected ProductModel() {
        super();
    }

    @Override
    public ProductID getId(){
        return new ProductID(id);
    }
    @Override
    public Item getProduction(){
        return producedItem;
    };
    @Override
    public Collection<Item> getRequirements(){
        return requirements.stream().map(itemModel -> (Item) itemModel).collect(Collectors.toSet());
    };
    @Override
    public Optional<Date> getDeletedAt(){
        return deletedAt;
    };

    @Override
    public void setProduction(Item production)

    @Override
    public void

}

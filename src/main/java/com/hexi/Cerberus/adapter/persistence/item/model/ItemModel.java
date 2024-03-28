package com.hexi.Cerberus.adapter.persistence.item.model;

import com.hexi.Cerberus.adapter.persistence.product.model.ProductModel;
import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.item.Unit;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Entity
@Table(name = "item_registry")

public class ItemModel extends Item {
    @Id
    UUID id;
    String name;
    Unit unit;
    Optional<Date> deletedAt;

    @OneToMany(mappedBy = "producedItem", cascade = CascadeType.ALL, orphanRemoval = true)
    Collection<ProductModel> products;
    @Deprecated
    protected ItemModel() {
        super();
    }
    @Override
    public ItemID getId(){
        return new ItemID(id);
    }
    @Override
    public String getName(){
        return name;
    }
    @Override
    public Unit getUnit(){
        return unit;
    };
    @Override
    public Optional<Date> getDeletedAt(){
        return deletedAt;
    }
    @Override
    public void setName(String n){
        name = n;
    }

    @Override
    public void setUnit(Unit u){
        unit = u;
    }
}

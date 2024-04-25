package com.hexi.Cerberus.adapter.persistence.item.base;

import com.hexi.Cerberus.adapter.persistence.product.base.ProductModel;
import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.item.Unit;
import jakarta.persistence.*;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Entity
@Table(name = "item_registry")

public class ItemModel extends Item {
    @EmbeddedId
    ItemID id;
    String name;
    Unit unit;
    @Temporal(TemporalType.TIMESTAMP)
    Date deletedAt;

    @OneToMany(mappedBy = "producedItem", cascade = CascadeType.ALL, orphanRemoval = true)
    Collection<ProductModel> products;

    @Deprecated
    private ItemModel() {
        super();
    }

    public ItemModel(ItemID itemID, String name, Unit unit) {
        this.id = new ItemID(itemID);
        this.name = name;
        this.unit = unit;
    }

    @Override
    public ItemID getId() {
        return new ItemID(id);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String n) {
        name = n;
    }

    @Override
    public Unit getUnit() {
        return unit;
    }

    @Override
    public void setUnit(Unit u) {
        unit = u;
    }

    @Override
    public Optional<Date> getDeletedAt() {
        return Optional.ofNullable(deletedAt);
    }
}

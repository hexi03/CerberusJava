package com.hexi.Cerberus.adapter.persistence.item.base;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "item_entries")
@Access(AccessType.FIELD)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    ItemModel item;
    int amount;

    public ItemEntry(ItemModel key, Integer value) {
        item = key;
        amount = value;
    }
}

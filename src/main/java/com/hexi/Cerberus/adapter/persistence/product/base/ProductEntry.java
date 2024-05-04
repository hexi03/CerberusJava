package com.hexi.Cerberus.adapter.persistence.product.base;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "product_entries")
@Access(AccessType.FIELD)
@Data
public class ProductEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    ProductModel product;
    int amount;

    public ProductEntry(ProductModel product, int amount) {
        this.product = product;
        this.amount = amount;
    }

    public ProductEntry() {
    }
}

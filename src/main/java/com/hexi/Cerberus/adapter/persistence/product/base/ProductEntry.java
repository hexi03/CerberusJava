package com.hexi.Cerberus.adapter.persistence.product.base;

import com.hexi.Cerberus.domain.product.Product;
import com.hexi.Cerberus.domain.product.ProductID;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_entries")
@Access(AccessType.FIELD)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    ProductModel product;
    int amount;

}

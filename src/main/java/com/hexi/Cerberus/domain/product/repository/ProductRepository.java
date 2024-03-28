package com.hexi.Cerberus.domain.product.repository;

import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.product.Product;
import com.hexi.Cerberus.domain.product.ProductID;
import com.hexi.Cerberus.infrastructure.repository.Repository;

import java.util.Optional;

public interface ProductRepository extends Repository<Product, ProductID> {

    Optional<Product> displayByItemId(ItemID id);
}

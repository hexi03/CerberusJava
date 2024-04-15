package com.hexi.Cerberus.domain.product.repository;

import com.hexi.Cerberus.adapter.persistence.product.base.ProductModel;
import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.product.Product;
import com.hexi.Cerberus.domain.product.ProductID;
import com.hexi.Cerberus.infrastructure.repository.Repository;

import java.util.Optional;

@org.springframework.stereotype.Repository
public interface ProductRepository<T extends Product, ID extends ProductID> extends Repository<T, ID> {
    //TODO
    Optional<ProductModel> findByItemId(ItemID id);
}

package com.hexi.Cerberus.adapter.persistence.product.impl;

import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.product.Product;
import com.hexi.Cerberus.domain.product.ProductID;
import com.hexi.Cerberus.domain.product.repository.ProductRepository;
import com.hexi.Cerberus.infrastructure.query.Query;

import java.util.List;
import java.util.Optional;

public class ProductRepositoryImpl implements ProductRepository {
    @Override
    public Optional<Product> displayByItemId(ItemID id) {
        return Optional.empty();
    }

    @Override
    public Optional<Product> displayById(ProductID id) {
        return Optional.empty();
    }

    @Override
    public List<Product> displayById(List<ProductID> productIDS) {
        return null;
    }

    @Override
    public List<Product> displayAll(Query query) {
        return null;
    }

    @Override
    public List<Product> displayAll() {
        return null;
    }

    @Override
    public Product append(Product user) {
        return null;
    }

    @Override
    public void update(Product user) {

    }

    @Override
    public void deleteById(ProductID id) {

    }

    @Override
    public boolean isExists(ProductID id) {
        return false;
    }
}

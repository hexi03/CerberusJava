package com.hexi.Cerberus.adapter.persistence.product.repository;

import com.hexi.Cerberus.adapter.persistence.product.base.ProductModel;
import com.hexi.Cerberus.domain.product.Product;
import com.hexi.Cerberus.domain.product.ProductID;
import com.hexi.Cerberus.domain.product.repository.ProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductRepository<T extends ProductModel,ID extends ProductID> extends ProductRepository<T,ID>, JpaRepository<T,ID> {
}

package com.hexi.Cerberus.adapter.persistence.product.repository;

import com.hexi.Cerberus.adapter.persistence.product.base.ProductModel;
import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.product.Product;
import com.hexi.Cerberus.domain.product.ProductID;
import com.hexi.Cerberus.domain.product.repository.ProductRepository;
import com.hexi.Cerberus.infrastructure.query.Query;
import jdk.jshell.spi.ExecutionControl;
import lombok.SneakyThrows;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JpaProductRepository<T extends ProductModel,ID extends ProductID> extends ProductRepository<T,ID>, JpaRepository<T,ID> {

    @SneakyThrows
    default List<T> findAllWithQuery(Query domainQuery){
        throw new ExecutionControl.NotImplementedException(" ");
    }
    @org.springframework.data.jpa.repository.Query("SELECT p FROM ProductModel p WHERE p.producedItem.id = :itemId")
    Optional<ProductModel> findByItemId(@Param("itemId") ItemID itemID);
}

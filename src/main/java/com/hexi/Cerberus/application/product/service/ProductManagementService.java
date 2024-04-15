package com.hexi.Cerberus.application.product.service;

import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.product.Product;
import com.hexi.Cerberus.domain.product.ProductID;
import com.hexi.Cerberus.domain.product.command.CreateProductCmd;
import com.hexi.Cerberus.domain.product.command.UpdateProductCmd;
import com.hexi.Cerberus.infrastructure.query.Query;

import java.util.List;
import java.util.Optional;

public interface ProductManagementService {
    List<Product> displayByProductionItem(ItemID id);

    Optional<Product> displayBy(ProductID id);

    List<Product> displayAllBy(Query query);

    List<Product> displayAll();

    Product create(CreateProductCmd cmd);

    void updateDetails(UpdateProductCmd cmd);

    void setDeleted(ProductID id);
}

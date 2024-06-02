package com.hexi.Cerberus.application.product.service;

import com.hexi.Cerberus.application.product.service.DTO.ProductDetailsDTO;
import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.product.ProductID;
import com.hexi.Cerberus.application.product.service.command.CreateProductCmd;
import com.hexi.Cerberus.application.product.service.command.UpdateProductCmd;
import com.hexi.Cerberus.infrastructure.query.Query;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
@Transactional
public interface ProductManagementService {
    Optional<ProductDetailsDTO> displayByProductionItem(ItemID id);

    Optional<ProductDetailsDTO> displayBy(ProductID id);

    List<ProductDetailsDTO> displayAllBy(Query query);

    List<ProductDetailsDTO> displayAll();

    ProductDetailsDTO create(CreateProductCmd cmd);

    void updateDetails(UpdateProductCmd cmd);

    void setDeleted(ProductID id);
}

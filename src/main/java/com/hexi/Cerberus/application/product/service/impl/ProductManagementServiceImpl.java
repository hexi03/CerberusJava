package com.hexi.Cerberus.application.product.service.impl;

import com.hexi.Cerberus.application.product.service.ProductManagementService;
import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.product.Product;
import com.hexi.Cerberus.domain.product.ProductFactory;
import com.hexi.Cerberus.domain.product.ProductID;
import com.hexi.Cerberus.domain.product.ProductUpdater;
import com.hexi.Cerberus.domain.product.command.CreateProductCmd;
import com.hexi.Cerberus.domain.product.command.UpdateProductCmd;
import com.hexi.Cerberus.domain.product.repository.ProductRepository;
import com.hexi.Cerberus.infrastructure.messaging.MessagePublisher;
import com.hexi.Cerberus.infrastructure.query.Query;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ProductManagementServiceImpl implements ProductManagementService {

    public final ProductRepository productRepository;
    public final MessagePublisher messagePublisher;
    public final ProductFactory productFactory;
    public final ProductUpdater productUpdater;

    @Override
    public List<Product> displayByProductionItem(ItemID id) {
        return null;
    }

    @Override
    public Optional<Product> displayBy(ProductID productID) {
        return productRepository.findById(productID);
    }

    @Override
    public List<Product> displayAllBy(Query query) {
        return productRepository.findAll(query);
    }

    @Override
    public List<Product> displayAll() {
        return productRepository.findAll();
    }

    @Override
    public Product create(CreateProductCmd cmd) {
        cmd.validate().onFailedThrow();
        Product product = productFactory.from(cmd);
        productRepository.append(product);
        messagePublisher.publish(product.edjectEvents());
        return product;

    }

    @Override
    public void updateDetails(UpdateProductCmd cmd) {
        cmd.validate().onFailedThrow();
        Optional<Product> product = productRepository.findById(cmd.getProductId());
        product.orElseThrow(() -> new RuntimeException(String.format("There are no product with id %s", cmd.getProductId().toString())));
        productUpdater.updateBy(product.get(), cmd);
        productRepository.update(product.get());
        messagePublisher.publish(product.get().edjectEvents());

    }

    @Override
    public void setDeleted(ProductID id) {
        Optional<Product> product = productRepository.findById(id);
        product.orElseThrow(() -> new RuntimeException(String.format("There are no warehouse with id %s", id.toString())));
        messagePublisher.publish(product.get().edjectEvents());
        productRepository.update(product.get());

    }
}

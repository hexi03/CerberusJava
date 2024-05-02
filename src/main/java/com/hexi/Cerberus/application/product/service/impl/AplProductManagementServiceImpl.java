package com.hexi.Cerberus.application.product.service.impl;

import com.hexi.Cerberus.application.product.service.DTO.ProductDetailsDTO;
import com.hexi.Cerberus.application.product.service.ProductDomainToDTOMapper;
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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class AplProductManagementServiceImpl implements ProductManagementService {

    public final ProductRepository productRepository;
    public final MessagePublisher messagePublisher;
    public final ProductFactory productFactory;
    public final ProductUpdater productUpdater;
    public final ProductDomainToDTOMapper productDomainToDtoMapper;

    @Override
    public Optional<ProductDetailsDTO> displayByProductionItem(ItemID id) {
        Optional<Product> product = productRepository.findByItemId(id);
        return product.map(productDomainToDtoMapper::productToDetailsDTO);
    }

    @Override
    public Optional<ProductDetailsDTO> displayBy(ProductID id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(productDomainToDtoMapper::productToDetailsDTO);
    }

    @Override
    public List<ProductDetailsDTO> displayAllBy(Query query) {
        return ((List<Product>)productRepository.findAllWithQuery(query)).stream()
                .map(productDomainToDtoMapper::productToDetailsDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDetailsDTO> displayAll() {
        return ((List<Product>)productRepository.findAll()).stream()
                .map(productDomainToDtoMapper::productToDetailsDTO)
                .collect(Collectors.toList());

    }

    @Override
    public ProductDetailsDTO create(CreateProductCmd cmd) {
        cmd.validate().onFailedThrow();
        Product product = productFactory.from(cmd);
        productRepository.append(product);
        messagePublisher.publish(product.edjectEvents());
        return productDomainToDtoMapper.productToDetailsDTO(product);

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

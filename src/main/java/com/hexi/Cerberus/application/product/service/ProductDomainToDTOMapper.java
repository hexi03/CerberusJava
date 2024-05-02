package com.hexi.Cerberus.application.product.service;

import com.hexi.Cerberus.application.item.service.DTO.ItemDetailsDTO;
import com.hexi.Cerberus.application.product.service.DTO.ProductDetailsDTO;
import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.product.Product;
import org.mapstruct.Mapper;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")

public interface ProductDomainToDTOMapper {

    default ProductDetailsDTO productToDetailsDTO(Product product) {
        return ProductDetailsDTO
                .builder()
                .id(product.getId())
                .productItemId(product.getProduction().getId())
                .requirements(product.getRequirements().stream().map(item -> item.getId()).collect(Collectors.toList()))
                .build();
    }
}
package com.hexi.Cerberus.application.product.service;

import com.hexi.Cerberus.application.product.service.DTO.ProductDetailsDTO;
import com.hexi.Cerberus.domain.product.Product;
import org.mapstruct.Mapper;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")

public interface ProductDomainToDTOMapper {

    default ProductDetailsDTO productToDetailsDTO(Product product) {
        return ProductDetailsDTO
                .builder()
                .id(product.getId())
                .producedItemId(product.getProduction().getId())
                .requirements(product.getRequirements().entrySet().stream().collect(Collectors.toMap(o -> o.getKey().getId(), o -> o.getValue())))
                .build();
    }
}
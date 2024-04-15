package com.hexi.Cerberus.adapter.web.rest.Registry;

import com.hexi.Cerberus.adapter.web.rest.Registry.DTO.ItemDetailsDTO;
import com.hexi.Cerberus.adapter.web.rest.Registry.DTO.ProductDetailsDTO;
import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.product.Product;
import org.mapstruct.Mapper;

import java.util.stream.Collectors;

@Mapper
public interface DomainToDTOMapper {
    default ItemDetailsDTO mapItemToDetailsDto(Item item) {
        return ItemDetailsDTO
                .builder()
                .id(item.getId())
                .name(item.getName())
                .units(item.getUnit().getUnit())
                .build();
    }

    ;

    default ProductDetailsDTO mapProductToDetailsDto(Product product) {
        return ProductDetailsDTO
                .builder()
                .id(product.getId())
                .productItemId(product.getProduction().getId())
                .requirements(product.getRequirements().stream().map(item -> item.getId()).collect(Collectors.toList()))
                .build();
    }

    ;
}

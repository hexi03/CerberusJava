package com.hexi.Cerberus.application.item.service;

import com.hexi.Cerberus.application.item.service.DTO.ItemDetailsDTO;
import com.hexi.Cerberus.application.product.service.DTO.ProductDetailsDTO;
import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.product.Product;
import org.mapstruct.Mapper;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")

public interface ItemDomainToDTOMapper {
    default ItemDetailsDTO itemToDetailsDTO(Item item) {
        return ItemDetailsDTO
                .builder()
                .id(item.getId())
                .name(item.getName())
                .units(item.getUnit().getUnit())
                .build();
    }

}

package com.hexi.Cerberus.domain.product;

import com.hexi.Cerberus.adapter.persistence.item.base.ItemModel;
import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.item.repository.ItemRepository;
import com.hexi.Cerberus.application.product.service.command.UpdateProductCmd;
import com.hexi.Cerberus.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class ProductUpdater {
    public final ProductRepository productRepository;
    public final ItemRepository itemRepository;

    public void updateBy(Product product, UpdateProductCmd cmd) {
        Map<Item, Integer> reqItems =
                cmd
                        .getRequirements()
                        .entrySet()
                        .stream()
                        .map(entry -> new AbstractMap.SimpleImmutableEntry<>(itemRepository.findById(entry.getKey()),entry.getValue()))
                        .filter(item -> item.getKey().isPresent())
                        .collect(Collectors.toMap(entry -> (ItemModel) entry.getKey().get(), entry -> entry.getValue()));;
        product.setRequirements(reqItems);

    }
}

package com.hexi.Cerberus.domain.product;

import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.item.repository.ItemRepository;
import com.hexi.Cerberus.domain.product.command.CreateProductCmd;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ProductFactory {
    public final ItemRepository itemRepository;
    public Product from(CreateProductCmd cmd) {
        Optional<Item> productItem = itemRepository.displayById(cmd.getItemId());
        productItem.orElseThrow(() -> new RuntimeException(String.format("There is no product item with id: %s", cmd.getItemId().toString())));
        List<Item> requirements = cmd
                .getRequirements()
                .stream()
                .map(itemRepository::displayById)
                .filter(item -> item.isPresent())
                .map(item -> item.get())
                .collect(Collectors.toList());

        return new Product(new ProductID(), productItem.get(), requirements);
    }
}

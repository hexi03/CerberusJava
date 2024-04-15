package com.hexi.Cerberus.adapter.persistence.product.factory;

import com.hexi.Cerberus.adapter.persistence.item.base.ItemModel;
import com.hexi.Cerberus.adapter.persistence.product.base.ProductModel;
import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.item.repository.ItemRepository;
import com.hexi.Cerberus.domain.product.Product;
import com.hexi.Cerberus.domain.product.ProductFactory;
import com.hexi.Cerberus.domain.product.ProductID;
import com.hexi.Cerberus.domain.product.command.CreateProductCmd;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class JpaProductFactoryImpl implements ProductFactory {
    public final ItemRepository itemRepository;

    public Product from(CreateProductCmd cmd) {
        Optional<Item> productItem = itemRepository.findById(cmd.getItemId());
        productItem.orElseThrow(() -> new RuntimeException(String.format("There is no product item with id: %s", cmd.getItemId().toString())));
        List<ItemModel> requirements = cmd
                .getRequirements()
                .stream()
                .map(itemID -> itemRepository.findById(itemID))
                .filter(item -> item.isPresent())
                .map(item -> (ItemModel) item.get())
                .collect(Collectors.toList());

        return new ProductModel(new ProductID(), productItem.get(), requirements);
    }
}

package com.hexi.Cerberus.domain.product;

import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.item.repository.ItemRepository;
import com.hexi.Cerberus.domain.product.command.UpdateProductCmd;
import com.hexi.Cerberus.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
public class ProductUpdater {
    public final ProductRepository productRepository;
    public final ItemRepository itemRepository;
    public void updateBy(Product product, UpdateProductCmd cmd) {
        List<Item> reqItems = itemRepository.displayById(cmd.getRequirements());
        product.setRequirements(reqItems);

    }
}

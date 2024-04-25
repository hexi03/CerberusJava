package com.hexi.Cerberus.domain.product;

import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.item.repository.ItemRepository;
import com.hexi.Cerberus.domain.product.command.UpdateProductCmd;
import com.hexi.Cerberus.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Component
public class ProductUpdater {
    public final ProductRepository productRepository;
    public final ItemRepository itemRepository;

    public void updateBy(Product product, UpdateProductCmd cmd) {
        List<Item> reqItems = itemRepository.findAllById(cmd.getRequirements()).stream().toList();
        product.setRequirements(reqItems);

    }
}

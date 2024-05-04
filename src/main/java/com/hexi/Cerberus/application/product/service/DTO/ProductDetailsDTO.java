package com.hexi.Cerberus.application.product.service.DTO;

import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.product.ProductID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
public class ProductDetailsDTO {
    ProductID id;
    ItemID producedItemId;
    Map<ItemID, Integer> requirements;
}

package com.hexi.Cerberus.application.product.service.DTO;

import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.product.ProductID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductDTO {
    ProductID id;
    ItemID producedItemId;
    Map<ItemID, Integer> requirements;
}

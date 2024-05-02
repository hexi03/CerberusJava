package com.hexi.Cerberus.application.product.service.DTO;

import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.product.ProductID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ProductDetailsDTO {
    ProductID id;
    ItemID productItemId;
    List<ItemID> requirements;
}

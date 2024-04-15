package com.hexi.Cerberus.adapter.web.rest.Registry.DTO;

import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.product.ProductID;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UpdateProductDTO {
    ProductID id;
    ItemID productItemId;
    List<ItemID> requirements;
}

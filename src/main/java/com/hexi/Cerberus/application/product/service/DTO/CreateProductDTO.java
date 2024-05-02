package com.hexi.Cerberus.application.product.service.DTO;

import com.hexi.Cerberus.domain.item.ItemID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductDTO {
    ItemID productItemId;
    List<ItemID> requirements;
}

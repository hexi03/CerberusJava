package com.hexi.Cerberus.adapter.web.rest.Registry.DTO;

import com.hexi.Cerberus.domain.item.ItemID;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class CreateProductDTO {
    ItemID productItemId;
    List<ItemID> requirements;
}

package com.hexi.Cerberus.adapter.web.rest.Registry.DTO;

import com.hexi.Cerberus.domain.item.ItemID;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateItemDTO {
    ItemID id;
    String name;
    String units;
}

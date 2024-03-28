package com.hexi.Cerberus.adapter.web.rest.Registry.DTO;

import com.hexi.Cerberus.domain.item.ItemID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ItemDetailsDTO {
    ItemID id;
    String name;
    String units;
}

package com.hexi.Cerberus.application.item.service.DTO;

import com.hexi.Cerberus.domain.item.ItemID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
public class ItemDetailsDTO {
    ItemID id;
    String name;
    String units;
}

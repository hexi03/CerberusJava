package com.hexi.Cerberus.application.item.service.DTO;

import com.hexi.Cerberus.domain.item.ItemID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateItemDTO {
    ItemID id;
    String name;
    String units;
}

package com.hexi.Cerberus.domain.item;

import com.hexi.Cerberus.domain.item.command.CreateItemCmd;
import org.springframework.stereotype.Component;

@Component
public interface ItemFactory {
    Item from(CreateItemCmd cmd);
}

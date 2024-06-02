package com.hexi.Cerberus.domain.item;

import com.hexi.Cerberus.application.item.service.command.CreateItemCmd;
import org.springframework.stereotype.Component;

@Component
public interface ItemFactory {
    Item from(CreateItemCmd cmd);
}

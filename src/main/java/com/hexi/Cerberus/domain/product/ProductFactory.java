package com.hexi.Cerberus.domain.product;

import com.hexi.Cerberus.application.product.service.command.CreateProductCmd;
import org.springframework.stereotype.Component;

@Component
public interface ProductFactory {
    Product from(CreateProductCmd cmd);
}

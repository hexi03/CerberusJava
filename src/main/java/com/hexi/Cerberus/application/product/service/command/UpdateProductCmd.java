package com.hexi.Cerberus.application.product.service.command;

import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.product.ProductID;
import com.hexi.Cerberus.infrastructure.ValidationResult;
import com.hexi.Cerberus.infrastructure.command.Command;
import com.hexi.Cerberus.infrastructure.command.CommandId;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Builder
public class UpdateProductCmd implements Command {
    CommandId id;
    ProductID productId;
    Map<ItemID, Integer> requirements;

    @Override
    public CommandId getId() {
        return id;
    }

    @Override
    public ValidationResult validate() {
        List<String> problems = new ArrayList<>();
        if (id == null) problems.add("Command id is null");
        if (productId == null) problems.add("Item id is null");
        if (requirements == null)
            problems.add("Requirement id(s) is null");

        return new ValidationResult(problems);
    }
}
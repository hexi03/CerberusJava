package com.hexi.Cerberus.domain.product.command;

import com.hexi.Cerberus.domain.item.ItemID;
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
public class CreateProductCmd implements Command {
    CommandId id;
    ItemID itemId;
    Map<ItemID,Integer> requirements;

    @Override
    public CommandId getId() {
        return id;
    }

    @Override
    public ValidationResult validate() {
        List<String> problems = new ArrayList<>();
        if (id == null) problems.add("Command id is null");
        if (itemId == null) problems.add("Item id is null");
        if (requirements == null || requirements.entrySet().stream().filter(userID -> userID == null).count() != 0)
            problems.add("Requirement id(s) is null");

        return new ValidationResult(problems);
    }
}

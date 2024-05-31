package com.hexi.Cerberus.application.mapper.DTO.problems;

import com.hexi.Cerberus.application.mapper.DTO.StateProblemDTO;
import com.hexi.Cerberus.domain.item.ItemID;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
@Getter
@Setter
public class InvalidStorageStateProblemDTO extends StateProblemDTO {
    private static final String InvalidStorageStateProblem = "InvalidStorageStateProblem";

    Map<ItemID, Integer> invalidStorageStatePositions;
    public InvalidStorageStateProblemDTO(Map<ItemID, Integer> invalidStorageStatePositions) {
        super(InvalidStorageStateProblem);
        this.invalidStorageStatePositions = invalidStorageStatePositions;
    }

    public InvalidStorageStateProblemDTO() {
        super(InvalidStorageStateProblem);
    }

  }

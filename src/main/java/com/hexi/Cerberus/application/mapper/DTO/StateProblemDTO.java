package com.hexi.Cerberus.application.mapper.DTO;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public abstract class StateProblemDTO {
    final String type;
    public StateProblemDTO(String type) {
        this.type = type;
    }
}

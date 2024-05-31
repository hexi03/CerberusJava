package com.hexi.Cerberus.application.mapper.DTO;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public abstract class StateWarningDTO {
    final String type;
    public StateWarningDTO(String type) {
        this.type = type;
    }
}

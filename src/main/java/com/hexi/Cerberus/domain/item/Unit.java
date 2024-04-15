package com.hexi.Cerberus.domain.item;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Unit {
    public final String unit;
}
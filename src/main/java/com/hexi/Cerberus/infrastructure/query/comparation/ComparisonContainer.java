package com.hexi.Cerberus.infrastructure.query.comparation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ComparisonContainer<T> {
    public final T value;
    public final Sign sign;
    public enum Sign {
        GREATER,
        LESS,
        EQUAL,
        GREATEREQUAL,
        LESSEQUAL
    }
}

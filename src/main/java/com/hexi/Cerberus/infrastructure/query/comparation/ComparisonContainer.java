package com.hexi.Cerberus.infrastructure.query.comparation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ComparisonContainer<T> implements ComparisonUnit<T> {
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



package com.hexi.Cerberus.infrastructure.query.comparation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ComparationContainer<T> {
    public enum Sign{
        GREATER,
        LESS,
        EQUAL,
        GREATEREQUAL,
        LESSEQUAL
    }

    public final T value;
    public final Sign sign;
}

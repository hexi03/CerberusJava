package com.hexi.Cerberus.infrastructure.query.comparation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class ComparisonSequence<T> implements ComparisonUnit<T> {
    public final List<ComparisonContainer<T>> containers;
    
}

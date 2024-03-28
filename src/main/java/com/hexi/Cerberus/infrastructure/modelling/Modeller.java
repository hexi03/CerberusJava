package com.hexi.Cerberus.infrastructure.modelling;

@FunctionalInterface
public interface Modeller<T> {
    T to(T model);
}

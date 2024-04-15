package com.hexi.Cerberus.domain.factorysite.exception;

public class FactorySiteAccessDeniedException extends RuntimeException {
    public FactorySiteAccessDeniedException() {
        super("Access to factory site denied");
    }
}

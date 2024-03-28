package com.hexi.Cerberus.domain.factorysite.exception;

public class FactorySiteNotFoundException extends RuntimeException{
    public FactorySiteNotFoundException() {
        super("Factory site not found");
    }
}

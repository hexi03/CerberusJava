package com.hexi.Cerberus.domain.exceptions;

public class FactorySiteNotFoundException extends RuntimeException{
    public FactorySiteNotFoundException() {
        super("Factory site not found");
    }
}

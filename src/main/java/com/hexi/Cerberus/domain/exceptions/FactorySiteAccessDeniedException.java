package com.hexi.Cerberus.domain.exceptions;

public class FactorySiteAccessDeniedException extends RuntimeException{
    public FactorySiteAccessDeniedException() {
        super("Access to factory site denied");
    }
}

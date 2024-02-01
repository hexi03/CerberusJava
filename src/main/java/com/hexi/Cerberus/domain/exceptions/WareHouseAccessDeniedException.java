package com.hexi.Cerberus.domain.exceptions;

public class WareHouseAccessDeniedException extends RuntimeException{
    public WareHouseAccessDeniedException() {
        super("Access to warehouse denied");
    }
}
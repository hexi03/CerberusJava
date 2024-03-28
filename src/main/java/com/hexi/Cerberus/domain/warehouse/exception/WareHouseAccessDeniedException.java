package com.hexi.Cerberus.domain.warehouse.exception;

public class WareHouseAccessDeniedException extends RuntimeException{
    public WareHouseAccessDeniedException() {
        super("Access to warehouse denied");
    }
}
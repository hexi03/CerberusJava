package com.hexi.Cerberus.domain.warehouse.exception;

public class WareHouseNotFoundException extends RuntimeException {
    public WareHouseNotFoundException() {
        super("Warehouse not found");
    }
}
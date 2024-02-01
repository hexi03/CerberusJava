package com.hexi.Cerberus.domain.exceptions;

public class WareHouseNotFoundException extends RuntimeException{
    public WareHouseNotFoundException() {
        super("Warehouse not found");
    }
}
package com.hexi.Cerberus.domain.exceptions;

public class DepartmentNotFoundException extends RuntimeException {
    public DepartmentNotFoundException() {
        super("Department not exists");
    }
}
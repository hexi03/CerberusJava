package com.hexi.Cerberus.domain.department.exception;

public class DepartmentNotFoundException extends RuntimeException {
    public DepartmentNotFoundException() {
        super("Department not exists");
    }
}
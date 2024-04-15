package com.hexi.Cerberus.domain.department.exception;

public class DepartmentAccessDeniedException extends RuntimeException {
    public DepartmentAccessDeniedException() {
        super("Access to department denied");
    }
}
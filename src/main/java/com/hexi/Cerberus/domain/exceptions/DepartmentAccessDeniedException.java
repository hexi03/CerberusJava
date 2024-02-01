package com.hexi.Cerberus.domain.exceptions;

public class DepartmentAccessDeniedException extends RuntimeException{
    public DepartmentAccessDeniedException() {
        super("Access to department denied");
    }
}
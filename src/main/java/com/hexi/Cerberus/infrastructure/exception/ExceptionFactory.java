package com.hexi.Cerberus.infrastructure.exception;

public class ExceptionFactory {

    public static Exception modelToModelConversion(Class aClass, Class bClass) {
        return new Exception(String.format("Conversion of specific models is not allowed (%s -X> %s )", aClass.getName(), bClass.getName()));
    }

}

package com.hexi.Cerberus.config;

import org.springframework.beans.factory.annotation.Value;

public class CerberusParameters {
    @Value("${report.expirationDuration}")
    public static long expirationDuration;
}

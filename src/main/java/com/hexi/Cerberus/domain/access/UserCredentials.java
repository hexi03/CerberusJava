package com.hexi.Cerberus.domain.access;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserCredentials {
    public final String email;
    public final String password;
}

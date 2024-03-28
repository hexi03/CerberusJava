package com.hexi.Cerberus.adapter.web.rest.auth.DTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AuthResponse {
    public final String token;
}

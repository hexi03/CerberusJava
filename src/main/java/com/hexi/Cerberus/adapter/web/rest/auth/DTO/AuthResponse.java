package com.hexi.Cerberus.adapter.web.rest.auth.DTO;

import com.hexi.Cerberus.domain.user.UserID;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AuthResponse {
    public final UserID userId;
    public final String token;

}

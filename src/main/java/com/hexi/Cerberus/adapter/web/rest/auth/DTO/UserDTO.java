package com.hexi.Cerberus.adapter.web.rest.auth.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
    String email;
    String password;
}

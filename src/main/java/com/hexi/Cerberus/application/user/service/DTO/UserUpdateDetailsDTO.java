package com.hexi.Cerberus.application.user.service.DTO;

import com.hexi.Cerberus.domain.user.UserID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserUpdateDetailsDTO {
    UserID id;
    String name;
    String password;
}
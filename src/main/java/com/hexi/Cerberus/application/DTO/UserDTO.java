package com.hexi.Cerberus.application.DTO;

import com.hexi.Cerberus.domain.commontypes.UserID;
import lombok.Data;

@Data
public class UserDTO {
    UserID id;
    String name;
}

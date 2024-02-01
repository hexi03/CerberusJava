package com.hexi.Cerberus.domain.entities;

import com.hexi.Cerberus.domain.commontypes.UserID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    UserID id;
    String name = "";
    String email = "";
    String passwordHash = "";


}

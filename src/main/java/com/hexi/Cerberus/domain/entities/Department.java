package com.hexi.Cerberus.domain.entities;

import com.hexi.Cerberus.domain.commontypes.DepartmentID;
import lombok.Data;

@Data
public class Department {
    DepartmentID id;
    String name;
}

package com.hexi.Cerberus.application.DTO;

import com.hexi.Cerberus.domain.commontypes.DepartmentID;
import lombok.Data;

@Data
public class DepartmentDTO {
    public DepartmentID id;
    public String name;
}

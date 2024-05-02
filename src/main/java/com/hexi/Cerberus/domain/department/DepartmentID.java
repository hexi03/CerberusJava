package com.hexi.Cerberus.domain.department;

import com.hexi.Cerberus.infrastructure.entity.EntityId;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@Component
public class DepartmentID implements EntityId<UUID>, Converter<String, DepartmentID> {
    public final UUID id;

    public DepartmentID() {
        id = UUID.randomUUID();
    }

    public DepartmentID(DepartmentID departmentID) {
        this.id = departmentID.getId();
    }

    public DepartmentID(String id) {
        this.id = UUID.fromString(id);
    }


    public String toString() {
        return this.getId().toString();
    }

    @Override
    public DepartmentID convert(String source) {
        return new DepartmentID(source);
    }
}

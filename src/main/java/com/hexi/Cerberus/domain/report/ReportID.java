package com.hexi.Cerberus.domain.report;

import com.hexi.Cerberus.infrastructure.entity.EntityId;
import lombok.Data;

import java.util.UUID;

@Data
public class ReportID implements EntityId<UUID> {
    public final UUID id;

    public ReportID() {
        id = UUID.randomUUID();
    }

    public ReportID(UUID id) {
        this.id = id;
    }

    public ReportID(ReportID id) {
        this.id = id.getId();
    }
    public ReportID(String id) { this.id = UUID.fromString(id); }

}

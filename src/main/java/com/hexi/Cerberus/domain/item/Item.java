package com.hexi.Cerberus.domain.item;

import com.hexi.Cerberus.infrastructure.aggregate.AggregateRoot;
import com.hexi.Cerberus.infrastructure.entity.Entity;
import com.hexi.Cerberus.infrastructure.event.DomainEvent;

import java.util.*;

public abstract class Item implements Entity, AggregateRoot {

    List<DomainEvent> events = new ArrayList<>();

    protected Item() {

    }

    @Override
    public void clearEvents() {
        events.clear();
    }

    @Override
    public List<DomainEvent> edjectEvents() {
        List<DomainEvent> ev = events;
        events = new ArrayList<>();
        return ev;
    }

    public abstract ItemID getId();

    public abstract String getName();

    public abstract void setName(String name);

    public abstract Unit getUnit();

    public abstract void setUnit(Unit unit);

    public abstract Optional<Date> getDeletedAt();

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Item other)) return false;
        if (!other.canEqual(this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (!Objects.equals(this$id, other$id)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (!Objects.equals(this$name, other$name)) return false;
        final Object this$unit = this.getUnit();
        final Object other$unit = other.getUnit();
        if (!Objects.equals(this$unit, other$unit)) return false;
        final Object this$deletedAt = this.getDeletedAt();
        final Object other$deletedAt = other.getDeletedAt();
        return Objects.equals(this$deletedAt, other$deletedAt);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Item;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $unit = this.getUnit();
        result = result * PRIME + ($unit == null ? 43 : $unit.hashCode());
        final Object $deletedAt = this.getDeletedAt();
        result = result * PRIME + ($deletedAt == null ? 43 : $deletedAt.hashCode());
        return result;
    }

    public String toString() {
        return "Item(id=" + this.getId() + ", name=" + this.getName() + ", unit=" + this.getUnit() + ", deletedAt=" + this.getDeletedAt() + ")";
    }
}

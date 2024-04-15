package com.hexi.Cerberus.domain.item;

import com.hexi.Cerberus.infrastructure.aggregate.AggregateRoot;
import com.hexi.Cerberus.infrastructure.entity.Entity;
import com.hexi.Cerberus.infrastructure.event.DomainEvent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
        if (!(o instanceof Item)) return false;
        final Item other = (Item) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$unit = this.getUnit();
        final Object other$unit = other.getUnit();
        if (this$unit == null ? other$unit != null : !this$unit.equals(other$unit)) return false;
        final Object this$deletedAt = this.getDeletedAt();
        final Object other$deletedAt = other.getDeletedAt();
        if (this$deletedAt == null ? other$deletedAt != null : !this$deletedAt.equals(other$deletedAt)) return false;
        return true;
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

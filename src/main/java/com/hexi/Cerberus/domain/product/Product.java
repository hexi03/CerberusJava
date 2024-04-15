package com.hexi.Cerberus.domain.product;

import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.infrastructure.aggregate.AggregateRoot;
import com.hexi.Cerberus.infrastructure.entity.Entity;
import com.hexi.Cerberus.infrastructure.event.DomainEvent;

import java.util.*;

public abstract class Product implements AggregateRoot, Entity {

    List<DomainEvent> events = new ArrayList<>();


    protected Product() {

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

    public abstract ProductID getId();

    public abstract Item getProduction();

    public abstract void setProduction(Item production);

    public abstract Collection<Item> getRequirements();

    public abstract void setRequirements(List<Item> requirements);

    public abstract Optional<Date> getDeletedAt();

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Product)) return false;
        final Product other = (Product) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$production = this.getProduction();
        final Object other$production = other.getProduction();
        if (this$production == null ? other$production != null : !this$production.equals(other$production))
            return false;
        final Object this$requirements = this.getRequirements();
        final Object other$requirements = other.getRequirements();
        if (this$requirements == null ? other$requirements != null : !this$requirements.equals(other$requirements))
            return false;
        final Object this$deletedAt = this.getDeletedAt();
        final Object other$deletedAt = other.getDeletedAt();
        if (this$deletedAt == null ? other$deletedAt != null : !this$deletedAt.equals(other$deletedAt)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Product;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $production = this.getProduction();
        result = result * PRIME + ($production == null ? 43 : $production.hashCode());
        final Object $requirements = this.getRequirements();
        result = result * PRIME + ($requirements == null ? 43 : $requirements.hashCode());
        final Object $deletedAt = this.getDeletedAt();
        result = result * PRIME + ($deletedAt == null ? 43 : $deletedAt.hashCode());
        return result;
    }

    public String toString() {
        return "Product(id=" + this.getId() + ", production=" + this.getProduction() + ", requirements=" + this.getRequirements() + ", deletedAt=" + this.getDeletedAt() + ")";
    }
}

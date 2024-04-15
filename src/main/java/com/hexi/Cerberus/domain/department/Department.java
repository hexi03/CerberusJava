package com.hexi.Cerberus.domain.department;

import com.google.common.collect.ImmutableCollection;
import com.google.errorprone.annotations.Immutable;
import com.hexi.Cerberus.domain.department.event.FactorySiteRegisteredEvent;
import com.hexi.Cerberus.domain.department.event.WareHouseRegisteredEvent;
import com.hexi.Cerberus.domain.factorysite.FactorySite;
import com.hexi.Cerberus.domain.factorysite.FactorySiteID;
import com.hexi.Cerberus.domain.warehouse.WareHouse;
import com.hexi.Cerberus.domain.warehouse.WareHouseID;
import com.hexi.Cerberus.infrastructure.aggregate.AggregateRoot;
import com.hexi.Cerberus.infrastructure.entity.SecuredEntity;
import com.hexi.Cerberus.infrastructure.event.DomainEvent;
import com.hexi.Cerberus.infrastructure.messaging.Message;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public abstract class Department implements SecuredEntity, AggregateRoot {

    List<DomainEvent> events = new ArrayList<>();

    protected Department() {

    }

    public void registerFactorySite(FactorySite factorySite) {
        Collection<DomainEvent> events = getEvents();
        addFactorySite(factorySite);
        factorySite.registerParentDepartment(this);
        events.add(new FactorySiteRegisteredEvent(this.getId(), factorySite.getId()));
    }

    public void registerWareHouse(WareHouse wareHouse) {
        Collection<DomainEvent> events = getEvents();
        addWareHouse(wareHouse);
        wareHouse.registerParentDepartment(this);
        events.add(new WareHouseRegisteredEvent(this.getId(), wareHouse.getId()));
    }

    public void deregisterFactorySite(FactorySiteID id) {

        FactorySite fs = removeFactorySite(id).orElseThrow();
        fs.resetParentDepartment();
    }

    public void deregisterWareHouse(WareHouseID id) {

        WareHouse wh = removeWareHouse(id).orElseThrow();
        wh.resetParentDepartment();
    }

    @Override
    public void clearEvents() {
        events.clear();
    }

    protected void dropEvents() {
        events = new ArrayList<>();
    }

    protected Collection<DomainEvent> getEvents() {
        return events;
    }

    @Override
    public Collection<DomainEvent> edjectEvents() {
        Collection<DomainEvent> ev = getEvents();
        dropEvents();
        return ev;
    }

    public abstract DepartmentID getId();

    public abstract String getName();

    public abstract void setName(String name);

    public abstract ImmutableCollection<FactorySite> getFactorySites();

    public abstract ImmutableCollection<WareHouse> getWareHouses();

    protected abstract void addFactorySite(FactorySite fs);

    protected abstract void addWareHouse(WareHouse wh);

    protected abstract void removeFactorySite(FactorySite fs);

    protected abstract void removeWareHouse(WareHouse wh);


    protected abstract Optional<FactorySite> removeFactorySite(FactorySiteID id);

    protected abstract Optional<WareHouse> removeWareHouse(WareHouseID id);


    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Department)) return false;
        final Department other = (Department) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$factorySites = this.getFactorySites();
        final Object other$factorySites = other.getFactorySites();
        if (this$factorySites == null ? other$factorySites != null : !this$factorySites.equals(other$factorySites))
            return false;
        final Object this$wareHouses = this.getWareHouses();
        final Object other$wareHouses = other.getWareHouses();
        if (this$wareHouses == null ? other$wareHouses != null : !this$wareHouses.equals(other$wareHouses))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Department;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $factorySites = this.getFactorySites();
        result = result * PRIME + ($factorySites == null ? 43 : $factorySites.hashCode());
        final Object $wareHouses = this.getWareHouses();
        result = result * PRIME + ($wareHouses == null ? 43 : $wareHouses.hashCode());
        return result;
    }

    public String toString() {
        return "Department(id=" + this.getId() + ", name=" + this.getName() + ", factorySites=" + this.getFactorySites() + ", wareHouses=" + this.getWareHouses() + ")";
    }
}

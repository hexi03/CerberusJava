package com.hexi.Cerberus.domain.department;

import com.hexi.Cerberus.domain.department.event.FactorySiteRegisteredEvent;
import com.hexi.Cerberus.domain.department.event.WareHouseRegisteredEvent;
import com.hexi.Cerberus.domain.factorysite.FactorySite;
import com.hexi.Cerberus.domain.warehouse.WareHouse;
import com.hexi.Cerberus.infrastructure.aggregate.AggregateRoot;
import com.hexi.Cerberus.infrastructure.entity.SecuredEntity;
import com.hexi.Cerberus.infrastructure.event.DomainEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Department implements SecuredEntity, AggregateRoot {
    DepartmentID id;
    String name;
    Collection<FactorySite> factorySites = new ArrayList<>();
    Collection<WareHouse> wareHouses = new ArrayList<>();

    List<DomainEvent> events = new ArrayList<>();

    public Department(DepartmentID id, String name) {
        this.id = id;
        this.name = name;

    }

    protected Department() {

    }


    public void registerFactorySite(FactorySite factorySite) {
        factorySites.add(factorySite);
        factorySite.setParentDepartment(this);
        events.add(new FactorySiteRegisteredEvent(this.getId(), factorySite.getId()));
    }

    public void registerWareHouse(WareHouse wareHouse) {
        wareHouses.add(wareHouse);
        wareHouse.setParentDepartment(this);
        events.add(new WareHouseRegisteredEvent(this.getId(), wareHouse.getId()));
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

    public DepartmentID getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Collection<FactorySite> getFactorySites() {
        return this.factorySites;
    }

    public Collection<WareHouse> getWareHouses() {
        return this.wareHouses;
    }

    public void setId(DepartmentID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

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

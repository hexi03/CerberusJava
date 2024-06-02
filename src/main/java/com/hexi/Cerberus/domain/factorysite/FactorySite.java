package com.hexi.Cerberus.domain.factorysite;

import com.google.common.collect.ImmutableCollection;
import com.hexi.Cerberus.domain.department.Department;
import com.hexi.Cerberus.domain.department.contract.DepartmentSlave;
import com.hexi.Cerberus.domain.warehouse.WareHouse;
import com.hexi.Cerberus.domain.warehouse.WareHouseID;
import com.hexi.Cerberus.infrastructure.aggregate.AggregateRoot;
import com.hexi.Cerberus.infrastructure.entity.SecuredEntity;
import com.hexi.Cerberus.infrastructure.event.DomainEvent;

import java.util.*;

public abstract class FactorySite implements SecuredEntity, AggregateRoot, DepartmentSlave {


    List<DomainEvent> events = new ArrayList<>();

    protected FactorySite() {

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

    public abstract FactorySiteID getId();

    public abstract Department getParentDepartment();

    protected abstract void setParentDepartment(Object o);

    public abstract String getName();

    public abstract void setName(String name);

    public abstract ImmutableCollection<WareHouse> getSuppliers();

    public abstract void addSupplier(WareHouse wh);

    public void addSuppliers(Collection<WareHouse> whs){
        whs.forEach(this::addSupplier);
    };
    public abstract void setSuppliers(Collection<WareHouse> whs);

    public abstract void removeSupplier(WareHouse wh);

    public abstract Optional<WareHouse> removeSupplier(WareHouseID id);

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof FactorySite other)) return false;
        if (!other.canEqual(this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (!Objects.equals(this$id, other$id)) return false;
        final Object this$parentDepartment = this.getParentDepartment().getId();
        final Object other$parentDepartment = other.getParentDepartment().getId();
        if (!Objects.equals(this$parentDepartment, other$parentDepartment))
            return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (!Objects.equals(this$name, other$name)) return false;
        final Object this$suppliers = this.getSuppliers().stream().map(o1 -> o1.getId()).toList();
        final Object other$suppliers = other.getSuppliers().stream().map(o1 -> o1.getId()).toList();
        return Objects.equals(this$suppliers, other$suppliers);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof FactorySite;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $parentDepartment = this.getParentDepartment();
        result = result * PRIME + ($parentDepartment == null ? 43 : $parentDepartment.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Collection<WareHouse> $suppliers = this.getSuppliers();
        result = result * PRIME + ($suppliers == null ? 43 : $suppliers.stream().map(o -> o.getId()).toList().hashCode());
        return result;
    }

    public String toString() {
        return "FactorySite(id=" + this.getId() + ", parentDepartment=" + this.getParentDepartment() + ", name=" + this.getName() + ", suppliers=" + this.getSuppliers().stream().map(o1 -> o1.getId()).toList() + ")";
    }

    @Override
    public void registerParentDepartment(Department d) {
        setParentDepartment(d);
    }

    @Override
    public void resetParentDepartment() {
        setParentDepartment(null);
    }


}

package com.hexi.Cerberus.domain.factorysite;

import com.hexi.Cerberus.domain.department.Department;
import com.hexi.Cerberus.domain.department.contract.DepartmentSlave;
import com.hexi.Cerberus.domain.warehouse.WareHouse;
import com.hexi.Cerberus.domain.warehouse.WareHouseID;
import com.hexi.Cerberus.infrastructure.aggregate.AggregateRoot;
import com.hexi.Cerberus.infrastructure.entity.SecuredEntity;
import com.hexi.Cerberus.infrastructure.event.DomainEvent;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

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

    public abstract Collection<WareHouse> getSuppliers();

    protected abstract void addSupplier(WareHouse fs);

    protected abstract void removeSupplier(WareHouse wh);

    protected abstract Optional<WareHouse> removeSupplier(WareHouseID id);

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof FactorySite)) return false;
        final FactorySite other = (FactorySite) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$parentDepartment = this.getParentDepartment();
        final Object other$parentDepartment = other.getParentDepartment();
        if (this$parentDepartment == null ? other$parentDepartment != null : !this$parentDepartment.equals(other$parentDepartment))
            return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$suppliers = this.getSuppliers();
        final Object other$suppliers = other.getSuppliers();
        if (this$suppliers == null ? other$suppliers != null : !this$suppliers.equals(other$suppliers)) return false;
        return true;
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
        final Object $suppliers = this.getSuppliers();
        result = result * PRIME + ($suppliers == null ? 43 : $suppliers.hashCode());
        return result;
    }

    public String toString() {
        return "FactorySite(id=" + this.getId() + ", parentDepartment=" + this.getParentDepartment() + ", name=" + this.getName() + ", suppliers=" + this.getSuppliers() + ")";
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

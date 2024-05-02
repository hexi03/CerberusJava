package com.hexi.Cerberus.domain.warehouse;

import com.hexi.Cerberus.domain.department.Department;
import com.hexi.Cerberus.domain.department.contract.DepartmentSlave;
import com.hexi.Cerberus.infrastructure.aggregate.AggregateRoot;
import com.hexi.Cerberus.infrastructure.entity.SecuredEntity;
import com.hexi.Cerberus.infrastructure.event.DomainEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public abstract class WareHouse implements SecuredEntity, AggregateRoot, DepartmentSlave {


    List<DomainEvent> events = new ArrayList<>();

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


    public abstract WareHouseID getId();

    public abstract Department getParentDepartment();

    protected abstract void setParentDepartment(Department parentDepartment);

    public abstract String getName();

    public abstract void setName(String name);

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof WareHouse other)) return false;
        if (!other.canEqual(this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (!Objects.equals(this$id, other$id)) return false;
        final Object this$parentDepartment = this.getParentDepartment();
        final Object other$parentDepartment = other.getParentDepartment();
        if (!Objects.equals(this$parentDepartment, other$parentDepartment))
            return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        return Objects.equals(this$name, other$name);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof WareHouse;
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
        return result;
    }

    public String toString() {
        return "WareHouse(id=" + this.getId() + ", parentDepartment=" + this.getParentDepartment() + ", name=" + this.getName() + ")";
    }
}

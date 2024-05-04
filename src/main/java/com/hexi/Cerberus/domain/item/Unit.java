package com.hexi.Cerberus.domain.item;

import jakarta.persistence.Embeddable;

@Embeddable
public class Unit {
    private String unit;

    public Unit(String unit) {
        this.unit = unit;
    }

    public Unit() {
    }

    public String getUnit() {
        return this.unit;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Unit)) return false;
        final Unit other = (Unit) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$unit = this.getUnit();
        final Object other$unit = other.getUnit();
        if (this$unit == null ? other$unit != null : !this$unit.equals(other$unit)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Unit;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $unit = this.getUnit();
        result = result * PRIME + ($unit == null ? 43 : $unit.hashCode());
        return result;
    }

    public String toString() {
        return "Unit(unit=" + this.getUnit() + ")";
    }
}
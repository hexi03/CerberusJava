package com.hexi.Cerberus.adapter.persistence.factorysite.base;

import com.hexi.Cerberus.adapter.persistence.department.base.DepartmentModel;
import com.hexi.Cerberus.adapter.persistence.warehouse.base.WareHouseModel;
import com.hexi.Cerberus.domain.department.Department;
import com.hexi.Cerberus.domain.factorysite.FactorySite;
import com.hexi.Cerberus.domain.factorysite.FactorySiteID;
import com.hexi.Cerberus.domain.warehouse.WareHouse;
import com.hexi.Cerberus.domain.warehouse.WareHouseID;
import jakarta.persistence.*;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Entity
@Table(name = "factorysite")

public class FactorySiteModel extends FactorySite {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private DepartmentModel department;

    private String name;

    @ManyToMany
    @JoinTable(name = "factorysite_warehouse_supply_assoc",
            joinColumns = @JoinColumn(name = "factorysite_id"),
            inverseJoinColumns = @JoinColumn(name = "warehouse_id"))
    private Collection<WareHouseModel> suppliers = new ArrayList<>();

    @Deprecated
    public FactorySiteModel(FactorySite fs) {
        super();
    }

    public FactorySiteModel(FactorySiteID factorySiteID, Department department, String name) {
        this.id = factorySiteID.getId();
        this.department = (DepartmentModel) department;
    }

    @Override
    public FactorySiteID getId() {
        return new FactorySiteID(id);
    }

    @Override
    public Department getParentDepartment() {
        return department;
    }

    @Override
    protected void setParentDepartment(Object o) {

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String n) {
        name = n;
    }

    @Override
    public Collection<WareHouse> getSuppliers() {
        return suppliers.stream().map(wareHouseModel -> (WareHouse) wareHouseModel).collect(Collectors.toSet());
    }

    @SneakyThrows
    @Override
    protected void addSupplier(WareHouse wh) {
        if (wh.getClass() == WareHouseModel.class) {
            suppliers.add((WareHouseModel) wh);
        } else {
            throw new Exception("Ivalid WareHouse child");
        }
    }

    @Override
    protected void removeSupplier(WareHouse wh) {
        suppliers = suppliers.stream().filter(wareHouse -> !wareHouse.getId().equals(wh.getId())).collect(Collectors.toList());
    }

    @Override
    protected Optional<WareHouse> removeSupplier(WareHouseID id) {
        Optional<WareHouseModel> item = suppliers.stream().filter(wareHouse -> wareHouse.getId().equals(id)).findAny();
        suppliers = suppliers.stream().filter(wareHouse -> !wareHouse.getId().equals(id)).collect(Collectors.toList());
        return item.map(Function.identity());
    }
}

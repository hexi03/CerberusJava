package com.hexi.Cerberus.adapter.persistence.factorysite.base;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
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
@Access(AccessType.FIELD)
public class FactorySiteModel extends FactorySite {

    @EmbeddedId
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private FactorySiteID id;

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

    @Deprecated
    private FactorySiteModel() {super();}

    public FactorySiteModel(FactorySiteID factorySiteID, Department department, String name) {
        this.id = new FactorySiteID(factorySiteID);
        this.name = name;
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
    public ImmutableCollection<WareHouse> getSuppliers() {
        return  ImmutableList.copyOf(suppliers.stream().map(wareHouseModel -> (WareHouse) wareHouseModel).collect(Collectors.toSet()));
    }

    @SneakyThrows
    @Override
    public void addSupplier(WareHouse wh) {
        if (wh.getClass() == WareHouseModel.class) {
            suppliers.add((WareHouseModel) wh);
        } else {
            throw new Exception("Ivalid WareHouse child");
        }
    }



    @Override
    public void setSuppliers(Collection<WareHouse> whs) {
        suppliers = whs.stream().map(wareHouse -> (WareHouseModel) wareHouse).collect(Collectors.toList());
    }

    @Override
    public void removeSupplier(WareHouse wh) {
        suppliers = suppliers.stream().filter(wareHouse -> !wareHouse.getId().equals(wh.getId())).collect(Collectors.toList());
    }

    @Override
    public Optional<WareHouse> removeSupplier(WareHouseID id) {
        Optional<WareHouseModel> item = suppliers.stream().filter(wareHouse -> wareHouse.getId().equals(id)).findAny();
        suppliers = suppliers.stream().filter(wareHouse -> !wareHouse.getId().equals(id)).collect(Collectors.toList());
        return item.map(Function.identity());
    }
}

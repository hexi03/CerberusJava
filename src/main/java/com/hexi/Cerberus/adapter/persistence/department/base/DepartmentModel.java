package com.hexi.Cerberus.adapter.persistence.department.base;


import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.hexi.Cerberus.adapter.persistence.factorysite.base.FactorySiteModel;
import com.hexi.Cerberus.adapter.persistence.warehouse.base.WareHouseModel;
import com.hexi.Cerberus.domain.department.Department;
import com.hexi.Cerberus.domain.department.DepartmentID;
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
@Table(name = "department")


public class DepartmentModel extends Department {

    @EmbeddedId
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private DepartmentID id;

    private String name;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = false)
    private Collection<FactorySiteModel> factorySites = new ArrayList<>();

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = false)
    private Collection<WareHouseModel> wareHouses = new ArrayList<>();


    @Deprecated
    public DepartmentModel() {
        super();
    }

    public DepartmentModel(DepartmentID departmentID, String name) {
        this.id = new DepartmentID(departmentID);
        this.name = name;
    }

    @Override
    public DepartmentID getId() {
        return new DepartmentID(id);
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
    public ImmutableCollection<FactorySite> getFactorySites() {
        return ImmutableList.copyOf(factorySites.stream().map(factorySiteModel -> (FactorySite) factorySiteModel).collect(Collectors.toList()));
    }

    @Override
    public ImmutableCollection<WareHouse> getWareHouses() {
        return ImmutableList.copyOf(wareHouses.stream().map(wareHouseModel -> (WareHouse) wareHouseModel).collect(Collectors.toList()));
    }

    @SneakyThrows
    @Override
    protected void addFactorySite(FactorySite fs) {
        if (fs.getClass() == FactorySiteModel.class) {
            factorySites.add((FactorySiteModel) fs);
        } else {
            throw new Exception("Ivalid FactorySite child");
        }
    }

    @SneakyThrows
    @Override
    protected void addWareHouse(WareHouse wh) {
        if (wh.getClass() == WareHouseModel.class) {
            wareHouses.add((WareHouseModel) wh);
        } else {
            throw new Exception("Ivalid WareHouse child");
        }
    }

    @Override
    protected void removeFactorySite(FactorySite fs) {
        factorySites = factorySites.stream().filter(factorySite -> !factorySite.getId().equals(fs.getId())).collect(Collectors.toList());
    }

    @Override
    protected void removeWareHouse(WareHouse wh) {
        wareHouses = wareHouses.stream().filter(wareHouse -> !wareHouse.getId().equals(wh.getId())).collect(Collectors.toList());
    }

    @Override
    protected Optional<FactorySite> removeFactorySite(FactorySiteID id) {
        Optional<FactorySiteModel> item = factorySites.stream().filter(factorySite -> factorySite.getId().equals(id)).findAny();
        factorySites = factorySites.stream().filter(factorySite -> !factorySite.getId().equals(id)).collect(Collectors.toList());
        return item.map(Function.identity());
    }

    @Override
    protected Optional<WareHouse> removeWareHouse(WareHouseID id) {
        Optional<WareHouseModel> item = wareHouses.stream().filter(wareHouse -> wareHouse.getId().equals(id)).findAny();
        wareHouses = wareHouses.stream().filter(wareHouse -> !wareHouse.getId().equals(id)).collect(Collectors.toList());
        return item.map(Function.identity());
    }

}
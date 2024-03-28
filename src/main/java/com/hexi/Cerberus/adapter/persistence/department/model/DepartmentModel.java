package com.hexi.Cerberus.adapter.persistence.department.model;


import com.hexi.Cerberus.adapter.persistence.factorysite.model.FactorySiteModel;
import com.hexi.Cerberus.adapter.persistence.warehouse.model.WareHouseModel;
import com.hexi.Cerberus.domain.department.Department;
import com.hexi.Cerberus.domain.department.DepartmentID;
import com.hexi.Cerberus.domain.factorysite.FactorySite;
import com.hexi.Cerberus.domain.warehouse.WareHouse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "department")


public class DepartmentModel extends Department {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private String name;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = false)
    private Collection<FactorySiteModel> factorySites = new ArrayList<>();

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = false)
    private Collection<WareHouseModel> wareHouses = new ArrayList<>();


    @Deprecated
    protected DepartmentModel() {
        super();
    }


    @Override
    public DepartmentID getId(){
        return new DepartmentID(id);
    }

    @Override
    public String getName(){
        return name;
    }

    @Override
    public Collection<FactorySite> getFactorySites(){
        return factorySites.stream().map(factorySiteModel -> (FactorySite) factorySiteModel).collect(Collectors.toSet());
    }

    @Override
    public Collection<WareHouse> getWareHouses(){
        return wareHouses.stream().map(wareHouseModel -> (WareHouse) wareHouseModel).collect(Collectors.toSet());
    }

    @Override
    public void setName(String n){
        name = n;
    }

    @Override
    public

//
//    @Override
//    public setWareHouses(Collection<WareHouse> whs){
//        wareHouses.clear();
//        wareHouses.addAll(whs.stream().map(wareHouse -> new WareHouseModel()))
//    }

//    @Override
//    public DepartmentID getId(){
//        return new DepartmentID(id);
//    }

//    @Override
//    public Collection<FactorySite> getFactorySites(){
//        return factorySites.stream().map(factorySiteModel -> (FactorySite) factorySiteModel).collect(Collectors.toSet());
//    }
}
package com.hexi.Cerberus.adapter.persistence.factorysite.model;

import com.hexi.Cerberus.adapter.persistence.department.model.DepartmentModel;
import com.hexi.Cerberus.adapter.persistence.warehouse.model.WareHouseModel;
import com.hexi.Cerberus.domain.department.Department;
import com.hexi.Cerberus.domain.factorysite.FactorySite;
import com.hexi.Cerberus.domain.factorysite.FactorySiteID;
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
    protected FactorySiteModel() {
        super();
    }
    @Override
    public FactorySiteID getId(){
        return new FactorySiteID(id);
    }
    @Override
    public Department getParentDepartment(){
        return department;
    }
    @Override
    public String getName(){
        return name;
    }
    @Override
    public Collection<WareHouse> getSuppliers(){
        return suppliers.stream().map(wareHouseModel -> (WareHouse) wareHouseModel).collect(Collectors.toSet());
    }

    @Override
    public void setName(String n){ name = n;}
}

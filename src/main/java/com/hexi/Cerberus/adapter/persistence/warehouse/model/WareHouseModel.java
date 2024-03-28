package com.hexi.Cerberus.adapter.persistence.warehouse.model;

import com.hexi.Cerberus.adapter.persistence.department.model.DepartmentModel;
import com.hexi.Cerberus.adapter.persistence.factorysite.model.FactorySiteModel;
import com.hexi.Cerberus.domain.warehouse.WareHouse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "warehouse")

@Getter
@Setter
public class WareHouseModel extends WareHouse {


    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private DepartmentModel department;

    private String name;

    @ManyToMany
    @JoinTable(name = "factorysite_warehouse_supply_assoc",
            joinColumns = @JoinColumn(name = "warehouse_id"),
            inverseJoinColumns = @JoinColumn(name = "factorysite_id"))
    private Collection<FactorySiteModel> consumers;

    @Deprecated
    protected WareHouseModel() {

    }
}

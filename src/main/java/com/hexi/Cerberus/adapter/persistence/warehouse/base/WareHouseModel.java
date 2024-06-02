package com.hexi.Cerberus.adapter.persistence.warehouse.base;

import com.hexi.Cerberus.adapter.persistence.department.base.DepartmentModel;
import com.hexi.Cerberus.adapter.persistence.factorysite.base.FactorySiteModel;
import com.hexi.Cerberus.domain.department.Department;
import com.hexi.Cerberus.domain.warehouse.WareHouse;
import com.hexi.Cerberus.domain.warehouse.WareHouseID;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Entity
@Table(name = "warehouse")
@Access(AccessType.FIELD)
@Getter
@Setter
public class WareHouseModel extends WareHouse {


    @EmbeddedId
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private WareHouseID id;

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
    private WareHouseModel() {

    }

    public WareHouseModel(WareHouseID wareHouseID, Department department, String name) {
        super();
        id = new WareHouseID(wareHouseID);
        this.department = (DepartmentModel) department;
        this.name = name;
    }

//    public WareHouseModel(WareHouse wh) throws Exception {
////        if (wh.getClass() != WareHouseBase.class) throw ExceptionFactory.modelToModelConversion(wh.getClass(), WareHouseModel.class);
//        id = wh.getId().getId();
//        department = wh.getParentDepartment();
//    }

    @Override
    public void registerParentDepartment(Department d) {

    }

    @Override
    public void resetParentDepartment() {

    }


    @Override
    public WareHouseID getId() {
        return new WareHouseID(id);
    }

    @Override
    public Department getParentDepartment() {
        return department;
    }

    @Override
    protected void setParentDepartment(Department parentDepartment) {
        this.department = (DepartmentModel) parentDepartment;
    }

    @Override
    public String getName() {
        return name;
    }
//    @Override
//    public Collection<FactorySiteModel> getConsumers(){
//        return consumers.stream().map(wareHouseModel -> (FactorySiteModel) wareHouseModel).collect(Collectors.toSet());
//    }

    @Override
    public void setName(String n) {
        name = n;
    }
}

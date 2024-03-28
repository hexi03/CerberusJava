package com.hexi.Cerberus.domain.warehouse;

import com.hexi.Cerberus.domain.department.Department;
import com.hexi.Cerberus.domain.warehouse.command.CreateWareHouseCmd;
import com.hexi.Cerberus.domain.warehouse.command.UpdateWareHouseDetailsCmd;
import com.hexi.Cerberus.infrastructure.aggregate.AggregateRoot;
import com.hexi.Cerberus.infrastructure.entity.SecuredEntity;
import com.hexi.Cerberus.infrastructure.event.DomainEvent;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class WareHouse implements SecuredEntity, AggregateRoot {
    WareHouseID id;
    Department parentDepartment;
    String name;
    
    List<DomainEvent> events = new ArrayList<>();

    public WareHouse(
            WareHouseID id,
            Department parentDepartment,
            String name
    ){
        this.id = id;
        this.name = name;
        this.parentDepartment = parentDepartment;
    }

    protected WareHouse(){

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



}

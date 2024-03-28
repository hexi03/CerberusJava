package com.hexi.Cerberus.adapter.persistence.department.model;

import com.hexi.Cerberus.domain.department.Department;
import com.hexi.Cerberus.infrastructure.modelling.Modeller;

public class DepartmentModeller implements Modeller<DepartmentModel> {

    public final Department domain;

    public DepartmentModeller(Department domain){
        this.domain = domain;
    }
    @Override
    public DepartmentModel to(DepartmentModel model) {
        model.setName(domain.getName());

        return model;
    }
}

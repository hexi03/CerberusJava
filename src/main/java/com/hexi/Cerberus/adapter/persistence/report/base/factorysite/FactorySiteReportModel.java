package com.hexi.Cerberus.adapter.persistence.report.base.factorysite;

import com.hexi.Cerberus.adapter.persistence.department.base.DepartmentModel;
import com.hexi.Cerberus.adapter.persistence.factorysite.base.FactorySiteModel;
import com.hexi.Cerberus.adapter.persistence.report.base.ReportModel;
import com.hexi.Cerberus.adapter.persistence.user.base.UserModel;
import com.hexi.Cerberus.domain.factorysite.FactorySite;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.domain.report.factorysite.FactorySiteReport;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;
import java.util.Optional;
@Entity
@Access(AccessType.FIELD)
@Getter
@Setter

public class FactorySiteReportModel extends ReportModel implements FactorySiteReport {
    @ManyToOne(cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    FactorySiteModel factorySite;

    protected FactorySiteReportModel() {
        super();
    }

    public FactorySiteReportModel(
            ReportID id,
            FactorySiteModel factorySite,
            Date createdAt,
            Date expirationDate,
            Optional<Date> deletedAt, UserModel creator) {
        super(id, (DepartmentModel) factorySite.getParentDepartment(), createdAt, expirationDate, deletedAt, creator);
        this.factorySite = factorySite;
    }

    public FactorySiteReportModel(
            ReportID id,
            FactorySiteModel factorySite,
            Date createdAt,
            Date expirationDate,
            UserModel creator) {
        super(id, (DepartmentModel) factorySite.getParentDepartment(), createdAt, expirationDate, creator);
        this.factorySite = factorySite;
    }

    @Override
    public FactorySite getFactorySite() {
        return (FactorySite)factorySite;
    }

//    public FactorySiteReportModel(
//            FactorySite factorySite,
//            Date createdAt,
//            Date expirationDate) {
//        super(factorySite.getParentDepartment(), createdAt, expirationDate);
//        this.factorySite = factorySite;
//    }


}

package com.hexi.Cerberus.domain.report;

import com.hexi.Cerberus.domain.department.Department;
import com.hexi.Cerberus.domain.report.query.filter.ReportStatus;
import com.hexi.Cerberus.infrastructure.aggregate.AggregateRoot;
import com.hexi.Cerberus.infrastructure.entity.SecuredEntity;
import com.hexi.Cerberus.infrastructure.event.DomainEvent;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public abstract class Report implements SecuredEntity, AggregateRoot {
    ReportID id;
    Department department;
    Date createdAt;
    Date expirationDate;
    ReportStatus status;


    Optional<Date> deletedAt;


    List<DomainEvent> events = new ArrayList<>();

    @Deprecated
    protected Report() {

    }


    public Report(ReportID id, Department department, Date createdAt, Date expirationDate, Optional<Date> deletedAt) {
        this.id = id;
        this.department = department;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
        this.expirationDate = expirationDate;
        updateStatus();
    }

    public Report(Department department, Date createdAt, Date expirationDate) {
        id = new ReportID();
        this.department = department;
        this.createdAt = createdAt;
        this.deletedAt = Optional.empty();
        this.expirationDate = expirationDate;
        updateStatus();

    }

    private void updateStatus() {
        if (deletedAt.isPresent()){
            this.status = ReportStatus.DELETED;
        }else{
//            if((new Date()).after(expirationDate)){
//                this.status = ReportStatus.ARCHIVED;
//            }
            this.status = ReportStatus.ACTIVE;
        }
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

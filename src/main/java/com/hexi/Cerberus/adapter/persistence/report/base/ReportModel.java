package com.hexi.Cerberus.adapter.persistence.report.base;

import com.hexi.Cerberus.domain.department.Department;
import com.hexi.Cerberus.domain.report.Report;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.domain.report.query.filter.ReportStatus;
import com.hexi.Cerberus.infrastructure.event.DomainEvent;
import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "report_registry")

public class ReportModel implements Report {
    @Id
    UUID id;
    @ManyToOne
    Department department;
    @Temporal(TemporalType.TIMESTAMP)
    Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    Date expirationDate;
    ReportStatus status;


    Optional<Date> deletedAt;

    @Transient
    transient List<DomainEvent> events = new ArrayList<>();


    public ReportModel(ReportID id, Department department, Date createdAt, Date expirationDate, Optional<Date> deletedAt) {
        this.id = id.getId();
        this.department = department;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
        this.expirationDate = expirationDate;
        updateStatus();
    }

    public ReportModel(ReportID id, Department department, Date createdAt, Date expirationDate) {
        this.id = id.getId();
        this.department = department;
        this.createdAt = createdAt;
        this.deletedAt = Optional.empty();
        this.expirationDate = expirationDate;
        updateStatus();
    }

//
//    UUID id;
//
//    @JoinColumn(name = "production_item_id")
//    ItemModel producedItem;
//    @ManyToMany
//    @JoinTable(name = "product_required_item_assoc",
//            joinColumns = @JoinColumn(name = "product_id"),
//            inverseJoinColumns = @JoinColumn(name = "requirement_item_id"))
//    List<ItemModel> requirements;
//    Optional<Date> deletedAt;

    @Deprecated
    public ReportModel() {
        super();
    }

    @Override
    public ReportStatus getStatus() {
        return status;
    }

    @Override
    public void setStatus(ReportStatus reportStatus) {
        this.status = reportStatus;
    }

    @Override
    public Optional<Date> getDeletedAt() {
        return deletedAt;
    }

    @Override
    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(Date date) {
        this.createdAt = date;
    }

    @Override
    public ReportID getId() {
        return new ReportID(id);
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

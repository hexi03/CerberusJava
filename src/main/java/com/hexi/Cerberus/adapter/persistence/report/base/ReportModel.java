package com.hexi.Cerberus.adapter.persistence.report.base;

import com.hexi.Cerberus.adapter.persistence.department.base.DepartmentModel;
import com.hexi.Cerberus.adapter.persistence.user.base.UserModel;
import com.hexi.Cerberus.domain.report.Report;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.domain.report.query.filter.ReportStatus;
import com.hexi.Cerberus.domain.user.User;
import com.hexi.Cerberus.infrastructure.event.DomainEvent;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "report_registry")
@Access(AccessType.FIELD)
public class ReportModel implements Report {
    @EmbeddedId
    ReportID id;
    @ManyToOne(cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    DepartmentModel department;
    @Temporal(TemporalType.TIMESTAMP)
    Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    Date expirationDate;
    @Transient
    ReportStatus status;
    @Temporal(TemporalType.TIMESTAMP)
    Date deletedAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    UserModel creator;

    @Transient
    transient List<DomainEvent> events = new ArrayList<>();


    public ReportModel(ReportID id, DepartmentModel department, Date createdAt, Date expirationDate, Optional<Date> deletedAt, UserModel creator) {
        this.id = new ReportID(id);
        this.department = department;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt.orElse(null);
        this.expirationDate = expirationDate;
        this.creator = creator;
        updateStatus();
    }

    public ReportModel(ReportID id, DepartmentModel department, Date createdAt, Date expirationDate, UserModel creator) {
        this.id = new ReportID(id);
        this.department = department;
        this.createdAt = createdAt;
        this.deletedAt = null;
        this.expirationDate = expirationDate;
        this.creator = creator;
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
    protected ReportModel() {
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
        return Optional.ofNullable(deletedAt);
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
    public Date getExpirationDate() {
        return expirationDate;
    }

    @Override
    public void setExpirationDate(Date date) {
        this.expirationDate = date;
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
    public User getCreator() {
        return  (User)creator;
    }

    @Override
    public void setCreator(User creator) {
        this.creator = (UserModel) creator;
    }

    @Override
    public List<DomainEvent> edjectEvents() {
        List<DomainEvent> ev = events;
        events = new ArrayList<>();
        return ev;
    }
}

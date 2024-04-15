package com.hexi.Cerberus.domain.report;

import com.hexi.Cerberus.domain.report.query.filter.ReportStatus;
import com.hexi.Cerberus.infrastructure.aggregate.AggregateRoot;
import com.hexi.Cerberus.infrastructure.entity.SecuredEntity;

import java.util.Date;
import java.util.Optional;


public interface Report extends SecuredEntity, AggregateRoot {


//
//    List<DomainEvent> events = new ArrayList<>();
//
//    @Deprecated
//    protected Report() {
//
//    }

    default void updateStatus() {
        if (getDeletedAt().isPresent()) {
            setStatus(ReportStatus.DELETED);
        } else {
//            if((new Date()).after(expirationDate)){
//                this.status = ReportStatus.ARCHIVED;
//            }
            setStatus(ReportStatus.ACTIVE);
        }
    }

    public abstract ReportID getId();

    public abstract ReportStatus getStatus();

    public abstract void setStatus(ReportStatus reportStatus);

    public abstract Optional<Date> getDeletedAt();

    Date getCreatedAt();

    void setCreatedAt(Date date);

//    @Override
//    public void clearEvents() {
//        events.clear();
//    }
//
//    @Override
//    public List<DomainEvent> edjectEvents() {
//        List<DomainEvent> ev = events;
//        events = new ArrayList<>();
//        return ev;
//    }


}

package com.hexi.Cerberus.domain.report;

import com.hexi.Cerberus.domain.report.query.filter.ReportStatus;
import com.hexi.Cerberus.domain.user.User;
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

    Date getExpirationDate();

    void setExpirationDate(Date date);

    ReportID getId();

    ReportStatus getStatus();

    void setStatus(ReportStatus reportStatus);

    Optional<Date> getDeletedAt();

    Date getCreatedAt();

    void setCreatedAt(Date date);

    User getCreator();

    void setCreator(User creator);

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

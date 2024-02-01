package com.hexi.Cerberus.application.impl;

import com.google.common.eventbus.Subscribe;
import com.hexi.Cerberus.adapter.web.sse.Messages.DepartmentsUpdatedSSEMessage;
import com.hexi.Cerberus.adapter.web.sse.Messages.FactorySitesUpdatedSSEMEssage;
import com.hexi.Cerberus.adapter.web.sse.Messages.WareHouseUpdatedSSEMessage;
import com.hexi.Cerberus.adapter.web.sse.SSEController;
import com.hexi.Cerberus.application.ClientSSEService;
import com.hexi.Cerberus.domain.events.DepartmentStorageUpdatedEvent;
import com.hexi.Cerberus.domain.events.FactorySiteStorageUpdatedEvent;
import com.hexi.Cerberus.domain.events.WareHouseStorageUpdatedEvent;

public class ClientSSEServiceImpl implements ClientSSEService {

    private final SSEController sseController;

    public ClientSSEServiceImpl(SSEController sseController) {
        this.sseController = sseController;
    }
    @Subscribe
    public void handleOrderPlaced(DepartmentStorageUpdatedEvent event) {
        sseController.sendSseEvent(new DepartmentsUpdatedSSEMessage());
    }

    @Subscribe
    public void handleOrderPlaced(FactorySiteStorageUpdatedEvent event) {
        sseController.sendSseEvent(new FactorySitesUpdatedSSEMEssage());
    }

    @Subscribe
    public void handleOrderPlaced(WareHouseStorageUpdatedEvent event) {
        sseController.sendSseEvent(new WareHouseUpdatedSSEMessage());
    }

//    @Subscribe
//    public void handleOrderPlaced(ReportStorageUpdatedEvent event) {
//        sseController.sendSseEvent(event);
//    }
//
//    @Subscribe
//    public void handleOrderPlaced(GroupUserStorageUpdatedEvent event) {
//        sseController.sendSseEvent(event);
//    }

}

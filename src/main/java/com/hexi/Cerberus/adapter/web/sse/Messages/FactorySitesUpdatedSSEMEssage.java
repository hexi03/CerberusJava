package com.hexi.Cerberus.adapter.web.sse.Messages;

public class FactorySitesUpdatedSSEMEssage implements SSEMessage {
    static String msg = "FACCTORY_SITES_UPDATED";


    @Override
    public String get_message() {
        return msg;
    }
}

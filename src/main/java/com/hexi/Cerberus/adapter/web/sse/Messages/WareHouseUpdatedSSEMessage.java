package com.hexi.Cerberus.adapter.web.sse.Messages;

public class WareHouseUpdatedSSEMessage implements SSEMessage{
    static String msg = "WAREHOUSES_UPDATED";

    @Override
    public String get_message() {
        return msg;
    }
}

package com.hexi.Cerberus.adapter.web.sse.Messages;

public class DepartmentsUpdatedSSEMessage implements SSEMessage {
    static String msg = "DEPARTMENTS_UPDATED";


    @Override
    public String get_message() {
        return msg;
    }
}

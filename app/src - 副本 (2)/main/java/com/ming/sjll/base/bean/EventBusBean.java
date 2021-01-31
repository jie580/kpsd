package com.ming.sjll.base.bean;

public class EventBusBean {
    private String eventName;

    public EventBusBean(String eventName) {
        this.eventName = eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventName() {
        return eventName;
    }
}

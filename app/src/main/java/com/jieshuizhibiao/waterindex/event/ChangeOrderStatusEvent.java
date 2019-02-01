package com.jieshuizhibiao.waterindex.event;

public class ChangeOrderStatusEvent {
    private String from;//从那个页面来
    private String msg;

    public ChangeOrderStatusEvent(String from, String msg) {
        this.from = from;
        this.msg = msg;
    }

    public String getFrom() {
        return from;
    }

    public String getMsg() {
        return msg;
    }
}

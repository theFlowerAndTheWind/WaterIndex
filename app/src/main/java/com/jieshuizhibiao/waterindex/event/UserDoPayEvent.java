package com.jieshuizhibiao.waterindex.event;

public class UserDoPayEvent {
    private String action;

    public UserDoPayEvent(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }
}

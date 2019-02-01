package com.jieshuizhibiao.waterindex.event;

public class ShouldRefreshEvent {
    //0 全部 1 进行中 2 已完成 3 已取消 4 申诉处理
    public static final String[] titles=new String[]{"全部","进行中","已完成","已取消","申诉处理"};
    private String title;

    public ShouldRefreshEvent(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}

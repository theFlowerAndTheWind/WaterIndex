package com.jieshuizhibiao.waterindex.beans;

import java.util.List;

/**
 * Created by songxiaotao on 2019/1/13.
 * Class Note:
 */

public class SystemMsgResponseBean {

    private List<SystemMsgList> lists;

    public List<SystemMsgList> getLists() {
        return lists;
    }

    public void setLists(List<SystemMsgList> lists) {
        this.lists = lists;
    }

    public static class SystemMsgList{
        int id;
        String title;
        String add_time;
        String content;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title == null ? "" : title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAdd_time() {
            return add_time == null ? "" : add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getContent() {
            return content == null ? "" : content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}

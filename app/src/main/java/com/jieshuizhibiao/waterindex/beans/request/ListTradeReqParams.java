package com.jieshuizhibiao.waterindex.beans.request;

/**
 * Created by songxiaotao on 2019/1/13.
 * Class Note:
 */

public class ListTradeReqParams {
    String type; 	//需求单类型 	字符串(string) 	否 	0 	0全部 1购买 2出售
    String page; 	//页码 	字符串(string) 	否 	1
    String page_size; 	//每页多少条 	字符串(string) 	否 	10

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPage_size() {
        return page_size;
    }

    public void setPage_size(String page_size) {
        this.page_size = page_size;
    }
}

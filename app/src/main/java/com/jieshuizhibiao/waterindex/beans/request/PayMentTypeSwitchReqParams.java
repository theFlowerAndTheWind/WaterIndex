package com.jieshuizhibiao.waterindex.beans.request;

/**
 * Created by songxiaotao on 2019/1/13.
 * Class Note:收款方式 开启|禁用
 */

public class PayMentTypeSwitchReqParams {
    String id; 	//收款信息id 	字符串(string) 	是
    int status; //开关状态

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

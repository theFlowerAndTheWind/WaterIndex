package com.jieshuizhibiao.waterindex.beans.request;

/**
 * Created by songxiaotao on 2019/1/13.
 * Class Note:
 */

public class AddTradeReqParams {

    String type; 	//需求类型 	字符串(string) 	是 		1购买 2出售
    String total; 	//数量 	字符串(string) 	是
    String pay_min; 	//最小交易量 	字符串(string) 	是
    String cond_pay_timeout; 	//超时时间 	字符串(string) 	否 		单位分钟
    String safe_pw; //资金密码

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPay_min() {
        return pay_min;
    }

    public void setPay_min(String pay_min) {
        this.pay_min = pay_min;
    }

    public String getCond_pay_timeout() {
        return cond_pay_timeout;
    }

    public void setCond_pay_timeout(String cond_pay_timeout) {
        this.cond_pay_timeout = cond_pay_timeout;
    }

    public String getSafe_pw() {
        return safe_pw == null ? "" : safe_pw;
    }

    public void setSafe_pw(String safe_pw) {
        this.safe_pw = safe_pw;
    }
}

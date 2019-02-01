package com.jieshuizhibiao.waterindex.beans.request;

/**
 * Created by songxiaotao on 2019/1/13.
 * Class Note:添加收款方式
 */

public class ChangePayMentTypeReqParams {

    int id; 	//收款方式id 	字符串(string) 	是 		pi_id
    String user_name; 	//姓名 	字符串(string) 	是
    String account_name; 	//卡号 	字符串(string) 	是
    String qrcode; 	//二维码 	字符串(string) 	否 		支付宝微信必填
    String bank_name; 	//银行名称 	字符串(string) 	否 		银行必填
    String safe_pw; 	//资金密码 	字符串(string) 	是
    String bank_detail_name; 	//支行信息 	字符串(string) 	是 		支行信息

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getSafe_pw() {
        return safe_pw;
    }

    public void setSafe_pw(String safe_pw) {
        this.safe_pw = safe_pw;
    }

    public String getBank_detail_name() {
        return bank_detail_name;
    }

    public void setBank_detail_name(String bank_detail_name) {
        this.bank_detail_name = bank_detail_name;
    }
}

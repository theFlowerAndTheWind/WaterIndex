package com.jieshuizhibiao.waterindex.beans.request;

/**
 * Created by songxiaotao on 2019/1/10.
 * Class Note:设置资金密码
 */

public class SetCaptialPassReqParams  {

    String token;	//身份码 	字符串(string) 	是
    String device_type; 	//设备类型 	字符串(string) 	是 		ios android
    String province; 	//省份 	字符串(string) 	是
    String city; 	//城市 	字符串(string) 	是
    String user_name; 	//真实姓名 	字符串(string) 	是
    String id_no; 	//身份证号码 	字符串(string) 	是
    String id_img_a; 	//正面 	字符串(string) 	是
    String id_img_b; 	//反面 	字符串(string) 	是
    String nick_name; 	//用户昵称 	字符串(string) 	是
    String safe_pw; 	//资金密码 	字符串(string) 	是
    String safe_pw_re; 	//重复资金密码 	字符串(string) 	是

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getId_no() {
        return id_no;
    }

    public void setId_no(String id_no) {
        this.id_no = id_no;
    }

    public String getId_img_a() {
        return id_img_a;
    }

    public void setId_img_a(String id_img_a) {
        this.id_img_a = id_img_a;
    }

    public String getId_img_b() {
        return id_img_b;
    }

    public void setId_img_b(String id_img_b) {
        this.id_img_b = id_img_b;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getSafe_pw() {
        return safe_pw;
    }

    public void setSafe_pw(String safe_pw) {
        this.safe_pw = safe_pw;
    }

    public String getSafe_pw_re() {
        return safe_pw_re;
    }

    public void setSafe_pw_re(String safe_pw_re) {
        this.safe_pw_re = safe_pw_re;
    }
}

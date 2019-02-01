package com.jieshuizhibiao.waterindex.beans.request;

/**
 * Created by songxiaotao on 2019/1/10.
 * Class Note:修改资金密码
 */

public class ChangeCapitalPassReqParams {

    String old_safe_pw; 	//旧资金密码 	字符串(string) 	是
    String safe_pw; 	//新资金密码 	字符串(string) 	是
    String safe_pw_re; 	//新资金密码 	字符串(string) 	是

    public String getOld_safe_pw() {
        return old_safe_pw;
    }

    public void  setOld_safe_pw(String old_safe_pw) {
        this.old_safe_pw = old_safe_pw;
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

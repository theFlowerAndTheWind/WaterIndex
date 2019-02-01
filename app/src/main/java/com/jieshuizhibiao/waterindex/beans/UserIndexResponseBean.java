package com.jieshuizhibiao.waterindex.beans;

/**
 * Created by songxiaotao on 2019/1/12.
 * Class Note:
 */

public class UserIndexResponseBean {
    String avatar; 	//头像 	字符串(string)
    String nick_name; 	//昵称 	字符串(string)
    String sys_msg; 	//系统消息 	字符串(string)

    public String getAvatar() {
        return avatar == null ? "" : avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNick_name() {
        return nick_name == null ? "" : nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getSys_msg() {
        return sys_msg == null ? "" : sys_msg;
    }

    public void setSys_msg(String sys_msg) {
        this.sys_msg = sys_msg;
    }
}

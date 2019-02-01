package com.jieshuizhibiao.waterindex.beans;

public class UserInfoResponseBean {
	private String avatar;//"default.pic"
	private String nick_name;//"sss"
	private String create_time;//":"2018-12-04"
	private String user_login;//":"134****4581",
	private int is_auth;//":3
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getNick_name() {
		return nick_name;
	}
	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getUser_login() {
		return user_login;
	}
	public void setUser_login(String user_login) {
		this.user_login = user_login;
	}
	public int getIs_auth() {
		return is_auth;
	}
	public void setIs_auth(int is_auth) {
		this.is_auth = is_auth;
	}
	
}

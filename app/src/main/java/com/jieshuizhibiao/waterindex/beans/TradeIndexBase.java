package com.jieshuizhibiao.waterindex.beans;

import java.util.List;

public class TradeIndexBase {
	private int total;
	private int page;
	private List<TradeIndex> list;
	private String is_login;
	private int is_auth;
	private String tip;

	public int getTotal() {
		return total;
	}

	public int getPage() {
		return page;
	}

	public List<TradeIndex> getList() {
		return list;
	}

	public String getIs_login() {
		return is_login;
	}

	public int getIs_auth() {
		return is_auth;
	}

	public String getTip() {
		return tip;
	}
}

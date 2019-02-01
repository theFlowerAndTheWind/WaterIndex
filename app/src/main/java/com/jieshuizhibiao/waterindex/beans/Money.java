package com.jieshuizhibiao.waterindex.beans;
public class Money {
	private String ds;// : "38.15119T",     可用
	private String ds_freeze;// : "56.94844T",     冻结
	private String total;// : "95.09963T",      总资产折合-吨水
	private String rmb;// : "285.29889元"    总资产折合-RMB

	public String getDs() {
		return ds;
	}

	public void setDs(String ds) {
		this.ds = ds;
	}

	public String getDs_freeze() {
		return ds_freeze;
	}

	public void setDs_freeze(String ds_freeze) {
		this.ds_freeze = ds_freeze;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getRmb() {
		return rmb;
	}

	public void setRmb(String rmb) {
		this.rmb = rmb;
	}
}

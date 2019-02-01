package com.jieshuizhibiao.waterindex.beans;

import java.util.List;

public class ListTradeResponseBean {
	private List<TradeList> trade_list;
	private int [] pay_info_list;
	private String total;

	public List<TradeList> getTrade_list() {
		return trade_list;
	}

	public void setTrade_list(List<TradeList> trade_list) {
		this.trade_list = trade_list;
	}

	public int[] getPay_info_list() {
		return pay_info_list;
	}

	public void setPay_info_list(int[] pay_info_list) {
		this.pay_info_list = pay_info_list;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public static class PayInfoList{

	}

	public static class TradeList {

		private int id;
		private String t_sn;
		private int uid;
		private int action_type;
		private String price;
		private String total;
		private String old_total;
		private String pay_type;
		private String pay_min;
		private int cond_user;
		private int cond_trade_count;
		private int cond_pay_timeout;
		private int status;
		private String add_time;
		private String action_type_text;
		private String done;
		private String status_text;
		private int can_del;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public void setT_sn(String t_sn) {
			this.t_sn = t_sn;
		}

		public String getT_sn() {
			return t_sn;
		}

		public void setUid(int uid) {
			this.uid = uid;
		}

		public int getUid() {
			return uid;
		}

		public void setAction_type(int action_type) {
			this.action_type = action_type;
		}

		public int getAction_type() {
			return action_type;
		}

		public void setPrice(String price) {
			this.price = price;
		}

		public String getPrice() {
			return price;
		}

		public void setOld_total(String old_total) {
			this.old_total = old_total;
		}

		public String getOld_total() {
			return old_total;
		}

		public void setTotal(String total) {
			this.total = total;
		}

		public String getTotal() {
			return total;
		}

		public void setPay_type(String pay_type) {
			this.pay_type = pay_type;
		}

		public String getPay_type() {
			return pay_type;
		}

		public void setPay_min(String pay_min) {
			this.pay_min = pay_min;
		}

		public String getPay_min() {
			return pay_min;
		}

		public void setCond_user(int cond_user) {
			this.cond_user = cond_user;
		}

		public int getCond_user() {
			return cond_user;
		}

		public void setCond_trade_count(int cond_trade_count) {
			this.cond_trade_count = cond_trade_count;
		}

		public int getCond_trade_count() {
			return cond_trade_count;
		}

		public void setCond_pay_timeout(int cond_pay_timeout) {
			this.cond_pay_timeout = cond_pay_timeout;
		}

		public int getCond_pay_timeout() {
			return cond_pay_timeout;
		}

		public void setStatus(int status) {
			this.status = status;
		}

		public int getStatus() {
			return status;
		}

		public void setAdd_time(String add_time) {
			this.add_time = add_time;
		}

		public String getAdd_time() {
			return add_time;
		}

		public void setAction_type_text(String action_type_text) {
			this.action_type_text = action_type_text;
		}

		public String getAction_type_text() {
			return action_type_text;
		}

		public void setDone(String done) {
			this.done = done;
		}

		public String getDone() {
			return done;
		}

		public void setStatus_text(String status_text) {
			this.status_text = status_text;
		}

		public String getStatus_text() {
			return status_text;
		}

		public void setCan_del(int can_del) {
			this.can_del = can_del;
		}

		public int getCan_del() {
			return can_del;
		}

	}
}

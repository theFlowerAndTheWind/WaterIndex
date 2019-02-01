package com.jieshuizhibiao.waterindex.beans;

import android.os.Parcel;
import android.os.Parcelable;

public class TradeIndex implements Parcelable {
	private int trade_id;
	private int trade_uid;
	private String avatar;
	private String user_nickname;
	private String sold;
	private String sold_rate;
	private String total;
	private String pay_type_bank_card;
	private String pay_type_alipay;
	private String pay_type_wechat;
	private String pay_min;
	private int pay_timeout;

	public void setTrade_id(int trade_id) {
		this.trade_id = trade_id;
	}

	public int getTrade_id() {
		return trade_id;
	}

	public void setTrade_uid(int trade_uid) {
		this.trade_uid = trade_uid;
	}

	public int getTrade_uid() {
		return trade_uid;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setUser_nickname(String user_nickname) {
		this.user_nickname = user_nickname;
	}

	public String getUser_nickname() {
		return user_nickname;
	}

	public void setSold(String sold) {
		this.sold = sold;
	}

	public String getSold() {
		return sold;
	}

	public void setSold_rate(String sold_rate) {
		this.sold_rate = sold_rate;
	}

	public String getSold_rate() {
		return sold_rate;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getTotal() {
		return total;
	}

	public void setPay_type_bank_card(String pay_type_bank_card) {
		this.pay_type_bank_card = pay_type_bank_card;
	}

	public String getPay_type_bank_card() {
		return pay_type_bank_card;
	}

	public void setPay_type_alipay(String pay_type_alipay) {
		this.pay_type_alipay = pay_type_alipay;
	}

	public String getPay_type_alipay() {
		return pay_type_alipay;
	}

	public void setPay_type_wechat(String pay_type_wechat) {
		this.pay_type_wechat = pay_type_wechat;
	}

	public String getPay_type_wechat() {
		return pay_type_wechat;
	}

	public void setPay_min(String pay_min) {
		this.pay_min = pay_min;
	}

	public String getPay_min() {
		return pay_min;
	}

	public void setPay_timeout(int pay_timeout) {
		this.pay_timeout = pay_timeout;
	}

	public int getPay_timeout() {
		return pay_timeout;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.trade_id);
		dest.writeInt(this.trade_uid);
		dest.writeString(this.avatar);
		dest.writeString(this.user_nickname);
		dest.writeString(this.sold);
		dest.writeString(this.sold_rate);
		dest.writeString(this.total);
		dest.writeString(this.pay_type_bank_card);
		dest.writeString(this.pay_type_alipay);
		dest.writeString(this.pay_type_wechat);
		dest.writeString(this.pay_min);
		dest.writeInt(this.pay_timeout);
	}

	public TradeIndex() {
	}

	protected TradeIndex(Parcel in) {
		this.trade_id = in.readInt();
		this.trade_uid = in.readInt();
		this.avatar = in.readString();
		this.user_nickname = in.readString();
		this.sold = in.readString();
		this.sold_rate = in.readString();
		this.total = in.readString();
		this.pay_type_bank_card = in.readString();
		this.pay_type_alipay = in.readString();
		this.pay_type_wechat = in.readString();
		this.pay_min = in.readString();
		this.pay_timeout = in.readInt();
	}

	public static final Creator<TradeIndex> CREATOR = new Creator<TradeIndex>() {
		@Override
		public TradeIndex createFromParcel(Parcel source) {
			return new TradeIndex(source);
		}

		@Override
		public TradeIndex[] newArray(int size) {
			return new TradeIndex[size];
		}
	};
}

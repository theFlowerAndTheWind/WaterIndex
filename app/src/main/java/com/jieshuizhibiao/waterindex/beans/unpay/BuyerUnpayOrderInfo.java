package com.jieshuizhibiao.waterindex.beans.unpay;

import android.os.Parcel;

public class BuyerUnpayOrderInfo extends BaseUnpayOrderInfo {
    private String seller_avatar;
    private String seller_nickname;

    public String getSeller_avatar() {
        return seller_avatar;
    }

    public String getSeller_nickname() {
        return seller_nickname;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.seller_avatar);
        dest.writeString(this.seller_nickname);
    }

    public BuyerUnpayOrderInfo() {
    }

    protected BuyerUnpayOrderInfo(Parcel in) {
        super(in);
        this.seller_avatar = in.readString();
        this.seller_nickname = in.readString();
    }

    public static final Creator<BuyerUnpayOrderInfo> CREATOR = new Creator<BuyerUnpayOrderInfo>() {
        @Override
        public BuyerUnpayOrderInfo createFromParcel(Parcel source) {
            return new BuyerUnpayOrderInfo(source);
        }

        @Override
        public BuyerUnpayOrderInfo[] newArray(int size) {
            return new BuyerUnpayOrderInfo[size];
        }
    };
}

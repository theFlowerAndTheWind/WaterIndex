package com.jieshuizhibiao.waterindex.beans.unpay;

import android.os.Parcel;

public class SellerUnpayOrderInfo extends BaseUnpayOrderInfo {
    private String buyer_avatar;
    private String buyer_nickname;

    public String getBuyer_avatar() {
        return buyer_avatar;
    }

    public String getBuyer_nickname() {
        return buyer_nickname;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.buyer_avatar);
        dest.writeString(this.buyer_nickname);
    }

    public SellerUnpayOrderInfo() {
    }

    protected SellerUnpayOrderInfo(Parcel in) {
        super(in);
        this.buyer_avatar = in.readString();
        this.buyer_nickname = in.readString();
    }

    public static final Creator<SellerUnpayOrderInfo> CREATOR = new Creator<SellerUnpayOrderInfo>() {
        @Override
        public SellerUnpayOrderInfo createFromParcel(Parcel source) {
            return new SellerUnpayOrderInfo(source);
        }

        @Override
        public SellerUnpayOrderInfo[] newArray(int size) {
            return new SellerUnpayOrderInfo[size];
        }
    };
}

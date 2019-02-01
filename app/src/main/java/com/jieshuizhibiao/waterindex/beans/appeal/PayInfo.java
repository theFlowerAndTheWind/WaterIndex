package com.jieshuizhibiao.waterindex.beans.appeal;

import android.os.Parcel;
import android.os.Parcelable;

public class PayInfo implements Parcelable {
    private long id;
    private int uid;
    private int type;
    private String user_name;
    private String bank_name;
    private String bank_detail_name;
    private String account_name;
    private String qrcode;
    private long add_time;
    private int status;

    public long getId() {
        return id;
    }

    public int getUid() {
        return uid;
    }

    public int getType() {
        return type;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getBank_name() {
        return bank_name;
    }

    public String getBank_detail_name() {
        return bank_detail_name;
    }

    public String getAccount_name() {
        return account_name;
    }

    public String getQrcode() {
        return qrcode;
    }

    public long getAdd_time() {
        return add_time;
    }

    public int getStatus() {
        return status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeInt(this.uid);
        dest.writeInt(this.type);
        dest.writeString(this.user_name);
        dest.writeString(this.bank_name);
        dest.writeString(this.bank_detail_name);
        dest.writeString(this.account_name);
        dest.writeString(this.qrcode);
        dest.writeLong(this.add_time);
        dest.writeInt(this.status);
    }

    public PayInfo() {
    }

    protected PayInfo(Parcel in) {
        this.id = in.readLong();
        this.uid = in.readInt();
        this.type = in.readInt();
        this.user_name = in.readString();
        this.bank_name = in.readString();
        this.bank_detail_name = in.readString();
        this.account_name = in.readString();
        this.qrcode = in.readString();
        this.add_time = in.readLong();
        this.status = in.readInt();
    }

    public static final Creator<PayInfo> CREATOR = new Creator<PayInfo>() {
        @Override
        public PayInfo createFromParcel(Parcel source) {
            return new PayInfo(source);
        }

        @Override
        public PayInfo[] newArray(int size) {
            return new PayInfo[size];
        }
    };
}

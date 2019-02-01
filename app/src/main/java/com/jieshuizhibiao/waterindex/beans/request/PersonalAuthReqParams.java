package com.jieshuizhibiao.waterindex.beans.request;

import android.os.Parcel;
import android.os.Parcelable;

public class PersonalAuthReqParams implements Parcelable {

    private String province;
    private String city;
    private String user_name;
    private String id_no;
    private String id_img_a;
    private String id_img_b;

    public PersonalAuthReqParams(){}

    public PersonalAuthReqParams(String province, String city,
                                 String user_name,
                                 String id_no, String id_img_a, String id_img_b) {
        super();
        this.province = province;
        this.city = city;
        this.user_name = user_name;
        this.id_no = id_no;
        this.id_img_a = id_img_a;
        this.id_img_b = id_img_b;
    }

    protected PersonalAuthReqParams(Parcel in) {
        province = in.readString();
        city = in.readString();
        user_name = in.readString();
        id_no = in.readString();
        id_img_a = in.readString();
        id_img_b = in.readString();
    }

    public static final Creator<PersonalAuthReqParams> CREATOR = new Creator<PersonalAuthReqParams>() {
        @Override
        public PersonalAuthReqParams createFromParcel(Parcel in) {
            return new PersonalAuthReqParams(in);
        }

        @Override
        public PersonalAuthReqParams[] newArray(int size) {
            return new PersonalAuthReqParams[size];
        }
    };

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getId_no() {
        return id_no;
    }

    public void setId_no(String id_no) {
        this.id_no = id_no;
    }

    public String getId_img_a() {
        return id_img_a;
    }

    public void setId_img_a(String id_img_a) {
        this.id_img_a = id_img_a;
    }

    public String getId_img_b() {
        return id_img_b;
    }

    public void setId_img_b(String id_img_b) {
        this.id_img_b = id_img_b;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(province);
        parcel.writeString(city);
        parcel.writeString(user_name);
        parcel.writeString(id_no);
        parcel.writeString(id_img_a);
        parcel.writeString(id_img_b);
    }
}

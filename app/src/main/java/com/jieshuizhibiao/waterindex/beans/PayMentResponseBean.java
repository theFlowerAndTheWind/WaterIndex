package com.jieshuizhibiao.waterindex.beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by songxiaotao on 2019/1/12.
 * Class Note: gson注解可以直接将一些特殊的对象解析出来，也可全部使用注解减少代码量
 * @SerializedName("1")
 * public TypeList L1;
 *
 * 使用注解 可以省去 get set方法
 */

public class PayMentResponseBean {

    @SerializedName("bank")
    public TypeList bank;
    @SerializedName("alipay")
    public TypeList alipay;
    @SerializedName("wechat")
    public TypeList wechat;

    public TypeList getBank() {
        return bank;
    }

    public void setBank(TypeList bank) {
        this.bank = bank;
    }

    public TypeList getAlipay() {
        return alipay;
    }

    public void setAlipay(TypeList alipay) {
        this.alipay = alipay;
    }

    public TypeList getWechat() {
        return wechat;
    }

    public void setWechat(TypeList wechat) {
        this.wechat = wechat;
    }

    public static class TypeList implements Parcelable{
        int id; 	//收款id 	int 		pi_id
        int type; 	//类型 	int		1银行卡 2支付宝 3微信
        String type_text; 	//type文案 	字符串(string)
        String account_name; 	//账号 	字符串(string)
        int isopen; 	//开关 	int 		1开 0关

        String link_text; 	//操作文字 	字符串(string) 		添加|编辑

        protected TypeList(Parcel in) {
            id = in.readInt();
            type = in.readInt();
            type_text = in.readString();
            account_name = in.readString();
            isopen = in.readInt();
            link_text = in.readString();
        }

        public static final Creator<TypeList> CREATOR = new Creator<TypeList>() {
            @Override
            public TypeList createFromParcel(Parcel in) {
                return new TypeList(in);
            }

            @Override
            public TypeList[] newArray(int size) {
                return new TypeList[size];
            }
        };

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getType_text() {
            return type_text == null ? "" : type_text;
        }

        public void setType_text(String type_text) {
            this.type_text = type_text;
        }

        public String getAccount_name() {
            return account_name == null ? "" : account_name;
        }

        public void setAccount_name(String account_name) {
            this.account_name = account_name;
        }

        public int getIsopen() {
            return isopen;
        }

        public void setIsopen(int isopen) {
            this.isopen = isopen;
        }

        public String getLink_text() {
            return link_text == null ? "" : link_text;
        }

        public void setLink_text(String link_text) {
            this.link_text = link_text;
        }

        /**
         * Describe the kinds of special objects contained in this Parcelable
         * instance's marshaled representation. For example, if the object will
         * include a file descriptor in the output of {@link #writeToParcel(Parcel, int)},
         * the return value of this method must include the
         * {@link #CONTENTS_FILE_DESCRIPTOR} bit.
         *
         * @return a bitmask indicating the set of special object types marshaled
         * by this Parcelable object instance.
         */
        @Override
        public int describeContents() {
            return 0;
        }

        /**
         * Flatten this object in to a Parcel.
         *
         * @param dest  The Parcel in which the object should be written.
         * @param flags Additional flags about how the object should be written.
         *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
         */
        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeInt(type);
            dest.writeString(type_text);
            dest.writeString(account_name);
            dest.writeInt(isopen);
            dest.writeString(link_text);
        }
    }

}

package com.jieshuizhibiao.waterindex.beans;

import android.os.Parcel;
import android.os.Parcelable;

public class ListOrder implements Parcelable {
    private String my_action;//方向  购买|出售
    private int other_uid;//对方uid
    private String other_nickname;//对方昵称
    private String other_avatar;//对方头像
    private String createtime;//创建时间
    private String status_text;//订单状态     已完成|进行中|已取消|申诉处理
    private String total;//数量    3.10000T
    private String rmb;//交易金额   9.30000元
    private long order_id;//订单id
    /**
     * buyerUnpay 买家未付款
     * buyerPaid 卖家未收款//
     * buyerAppeal 申诉中
     * buyerSucc 已完成
     * cancelOrder 已取消
     *
     * sellerUnpay 买家未付款
     * sellerPaid 卖家未收款//
     * sellerAppeal 申诉中
     * sellerSucc 已完成
     * sellerCancel 已取消
     */
    private String next_step;//下一步动作
    public void setMy_action(String my_action) {
        this.my_action = my_action;
    }
    public String getMy_action() {
        return my_action;
    }

    public void setOther_uid(int other_uid) {
        this.other_uid = other_uid;
    }
    public int getOther_uid() {
        return other_uid;
    }

    public void setOther_nickname(String other_nickname) {
        this.other_nickname = other_nickname;
    }
    public String getOther_nickname() {
        return other_nickname;
    }

    public void setOther_avatar(String other_avatar) {
        this.other_avatar = other_avatar;
    }
    public String getOther_avatar() {
        return other_avatar;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }
    public String getCreatetime() {
        return createtime;
    }

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }
    public String getStatus_text() {
        return status_text;
    }

    public void setTotal(String total) {
        this.total = total;
    }
    public String getTotal() {
        return total;
    }

    public void setRmb(String rmb) {
        this.rmb = rmb;
    }
    public String getRmb() {
        return rmb;
    }

    public void setOrder_id(long order_id) {
        this.order_id = order_id;
    }
    public long getOrder_id() {
        return order_id;
    }

    public void setNext_step(String next_step) {
        this.next_step = next_step;
    }
    public String getNext_step() {
        return next_step;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.my_action);
        dest.writeInt(this.other_uid);
        dest.writeString(this.other_nickname);
        dest.writeString(this.other_avatar);
        dest.writeString(this.createtime);
        dest.writeString(this.status_text);
        dest.writeString(this.total);
        dest.writeString(this.rmb);
        dest.writeLong(this.order_id);
        dest.writeString(this.next_step);
    }

    public ListOrder() {
    }

    protected ListOrder(Parcel in) {
        this.my_action = in.readString();
        this.other_uid = in.readInt();
        this.other_nickname = in.readString();
        this.other_avatar = in.readString();
        this.createtime = in.readString();
        this.status_text = in.readString();
        this.total = in.readString();
        this.rmb = in.readString();
        this.order_id = in.readLong();
        this.next_step = in.readString();
    }

    public static final Creator<ListOrder> CREATOR = new Creator<ListOrder>() {
        @Override
        public ListOrder createFromParcel(Parcel source) {
            return new ListOrder(source);
        }

        @Override
        public ListOrder[] newArray(int size) {
            return new ListOrder[size];
        }
    };
}

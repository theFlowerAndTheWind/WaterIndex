package com.jieshuizhibiao.waterindex.beans.paid;

import com.jieshuizhibiao.waterindex.beans.appeal.PayInfo;

public class SellerPaidResponse {
    private SellerPaidOrderInfo order_info;
    private PayInfo pay_info;

    public SellerPaidOrderInfo getOrder_info() {
        return order_info;
    }

    public PayInfo getPay_info() {
        return pay_info;
    }
}

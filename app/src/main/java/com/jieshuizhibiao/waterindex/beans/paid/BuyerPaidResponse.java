package com.jieshuizhibiao.waterindex.beans.paid;

import com.jieshuizhibiao.waterindex.beans.appeal.PayInfo;

public class BuyerPaidResponse {
    private BuyerPaidOrderInfo order_info;
    private PayInfo pay_info;

    public BuyerPaidOrderInfo getOrder_info() {
        return order_info;
    }

    public PayInfo getPay_info() {
        return pay_info;
    }
}

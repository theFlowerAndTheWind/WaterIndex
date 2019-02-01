package com.jieshuizhibiao.waterindex.beans.succ;

import com.jieshuizhibiao.waterindex.beans.appeal.PayInfo;

public class SuccResponse {
    private SuccOrderInfo order_info;
    private PayInfo pay_info;

    public SuccOrderInfo getOrder_info() {
        return order_info;
    }

    public PayInfo getPay_info() {
        return pay_info;
    }
}

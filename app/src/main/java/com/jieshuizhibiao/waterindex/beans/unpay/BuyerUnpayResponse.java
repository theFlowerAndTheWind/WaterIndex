package com.jieshuizhibiao.waterindex.beans.unpay;

import com.jieshuizhibiao.waterindex.beans.appeal.PayInfo;

import java.util.List;

public class BuyerUnpayResponse {
    private BuyerUnpayOrderInfo order_info;
    private List<PayInfo> pay_info_list;

    public BuyerUnpayOrderInfo getOrder_info() {
        return order_info;
    }

    public List<PayInfo> getPay_info_list() {
        return pay_info_list;
    }
}

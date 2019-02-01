package com.jieshuizhibiao.waterindex.beans.unpay;

import com.jieshuizhibiao.waterindex.beans.appeal.PayInfo;

import java.util.List;

public class SellerUnpayResponse {
    private SellerUnpayOrderInfo order_info;
    private List<PayInfo> pay_info_list;

    public SellerUnpayOrderInfo getOrder_info() {
        return order_info;
    }

    public List<PayInfo> getPay_info_list() {
        return pay_info_list;
    }
}

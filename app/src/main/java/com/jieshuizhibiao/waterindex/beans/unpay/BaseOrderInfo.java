package com.jieshuizhibiao.waterindex.beans.unpay;

public class BaseOrderInfo {
    //    "order_info":{
//     *                  "title":"出售节水指标",
//     *                  "expire_time":"不限制",
//     *                  "rmb":"3.00000元",
//     *                  "total":"1.00000T",
//     *                  "price":"3.00000元\/T",
//     *                  "order_sn":"20190110153159246886",
//     *                  "pay_code":"246886",
//     *                  "createtime":"2019-01-10 15:31:59",
//     *                  "buyer_avatar":"https:\/\/www.jieshuizhibiao.com\/upload\/admin\/20190111\/2cb733cf51ef8fe5f9c955bc78919c6c.jpg",
//     *                  "buyer_nickname":"石头",
//     *                  "order_id":"57"
//                *              },
    private String title;
    private String expire_time;
    private String rmb;
    private String total;
    private String price;
    private String order_sn;
    private String pay_code;
    private String createtime;


    private String order_id;

    public String getTitle() {
        return title;
    }

    public String getExpire_time() {
        return expire_time;
    }

    public String getRmb() {
        return rmb;
    }

    public String getTotal() {
        return total;
    }

    public String getPrice() {
        return price;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public String getPay_code() {
        return pay_code;
    }

    public String getCreatetime() {
        return createtime;
    }

    public String getOrder_id() {
        return order_id;
    }
}
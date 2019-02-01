package com.jieshuizhibiao.waterindex.contract.model.callback;

/**
 * 买家卖家共用
 */
public interface CancelOrderCallback {
    void onCancleSucc(Object bean);
    void onCancleFail(String msg);
}

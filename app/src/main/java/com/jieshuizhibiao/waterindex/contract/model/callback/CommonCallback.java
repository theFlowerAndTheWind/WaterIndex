package com.jieshuizhibiao.waterindex.contract.model.callback;

public interface CommonCallback {

    void onRequestSuccess(Object bean);
    void onRequestFailed(String msg);
}

package com.jieshuizhibiao.waterindex.contract.model.callback;

public interface ThirdRequestCallback {
    void onThirdRequestSucc(Object o);
    void onThirdRequestFail(String msg);

}

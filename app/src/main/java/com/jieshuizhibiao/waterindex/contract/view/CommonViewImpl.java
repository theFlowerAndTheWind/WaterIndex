package com.jieshuizhibiao.waterindex.contract.view;

import com.jieshuizhibiao.waterindex.contract.IBaseViewImpl;

public interface CommonViewImpl  extends IBaseViewImpl {

    void onRequestSuccess(Object bean);
    void onRequestFailed(String msg);
}

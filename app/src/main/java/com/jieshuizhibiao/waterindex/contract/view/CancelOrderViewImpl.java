package com.jieshuizhibiao.waterindex.contract.view;

import com.jieshuizhibiao.waterindex.contract.IBaseViewImpl;

public interface CancelOrderViewImpl extends IBaseViewImpl {
    void onCancelSucc(Object bean);
    void onCancelFail(String msg);
}

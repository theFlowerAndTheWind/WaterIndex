package com.jieshuizhibiao.waterindex.contract.view;

import com.jieshuizhibiao.waterindex.contract.IBaseViewImpl;

public interface ThirdRequestViewImpl extends IBaseViewImpl {
    void onThirdRequestSucc(Object o);
    void onThirdRequestFail(String msg);
}

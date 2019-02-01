package com.jieshuizhibiao.waterindex.contract.model.callback;

import com.jieshuizhibiao.waterindex.beans.SysConfigResponseBean;

public interface SecondRequestCallback {
    void onSecondRequstSuccess(Object bean);
    void onSecondRequstFailed(String msg);
}

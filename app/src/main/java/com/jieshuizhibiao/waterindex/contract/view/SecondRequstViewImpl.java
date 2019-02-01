package com.jieshuizhibiao.waterindex.contract.view;

import com.jieshuizhibiao.waterindex.contract.IBaseViewImpl;

/**
 * Created by WanghongHe on 2019/1/21 15:07.
 */

public interface SecondRequstViewImpl extends IBaseViewImpl{
    void onSecondRequstSuccess(Object bean);
    void onSecondRequstFailed(String msg);
}

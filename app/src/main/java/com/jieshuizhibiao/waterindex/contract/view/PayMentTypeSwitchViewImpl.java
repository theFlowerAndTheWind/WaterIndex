package com.jieshuizhibiao.waterindex.contract.view;

import com.jieshuizhibiao.waterindex.contract.IBaseViewImpl;

/**
 * Created by songxiaotao on 2019/1/13.
 * Class Note:
 */

public interface PayMentTypeSwitchViewImpl extends IBaseViewImpl {
    void onPaymentTypeSwitchSuccess();
    void onPaymentTypeSwitcFailed(String msg);
}

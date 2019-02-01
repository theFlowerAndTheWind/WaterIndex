package com.jieshuizhibiao.waterindex.contract.view;

import com.jieshuizhibiao.waterindex.contract.IBaseViewImpl;

import java.util.Map;

/**
 * Created by songxiaotao on 2019/1/13.
 * Class Note:
 */

public interface ChangePaymentTypeViewImpl extends IBaseViewImpl {
    void onChangeContentsLegal();
    void onChangeEdtContentsIllegal(Map<String, Boolean> verify);
    void onChangeEdtContentsIllegalBank(Map<String, Boolean> verify);
    void onChangePaymentTyoeSuccess();
    void onChangePaymentTypeFalied(String msg);
}

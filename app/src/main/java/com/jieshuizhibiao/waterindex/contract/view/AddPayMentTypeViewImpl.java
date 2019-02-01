package com.jieshuizhibiao.waterindex.contract.view;

import com.jieshuizhibiao.waterindex.contract.IBaseViewImpl;

import java.util.Map;

/**
 * Created by songxiaotao on 2019/1/13.
 * Class Note:
 */

public interface AddPayMentTypeViewImpl extends IBaseViewImpl {
    void onAddEdtContentsLegal();
    void onAddEdtContentsIllegal(Map<String, Boolean> verify);
    void onAddEdtContentsIllegalBank(Map<String, Boolean> verify);
    void onAddPaymentTypeSuccess();
    void onAddPaymentTypeFailed(String msg);
}

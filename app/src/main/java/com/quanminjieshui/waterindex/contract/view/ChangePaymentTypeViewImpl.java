package com.quanminjieshui.waterindex.contract.view;

import com.quanminjieshui.waterindex.contract.IBaseViewImpl;

/**
 * Created by songxiaotao on 2019/1/13.
 * Class Note:
 */

public interface ChangePaymentTypeViewImpl extends IBaseViewImpl {
    void changePaymentTyoeSuccess();
    void changePaymentTypeFalied(String msg);
}

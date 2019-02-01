package com.jieshuizhibiao.waterindex.contract.view;

import com.jieshuizhibiao.waterindex.beans.PayMentResponseBean;
import com.jieshuizhibiao.waterindex.contract.IBaseViewImpl;

/**
 * Created by songxiaotao on 2019/1/17.
 * Class Note:
 */

public interface PayMentTypeViewImpl extends IBaseViewImpl {
    void onPayMentTypeSuccess(PayMentResponseBean payMentResponseBean);
    void onPayMentTypeFailed(String msg);
}

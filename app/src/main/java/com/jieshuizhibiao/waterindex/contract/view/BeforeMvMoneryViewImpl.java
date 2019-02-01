package com.jieshuizhibiao.waterindex.contract.view;

import com.jieshuizhibiao.waterindex.beans.BeforeMvMoneyResponse;
import com.jieshuizhibiao.waterindex.beans.MoveMoneryBean;
import com.jieshuizhibiao.waterindex.contract.IBaseViewImpl;

/**
 * Created by songxiaotao on 2019/1/13.
 * Class Note:
 */

public interface BeforeMvMoneryViewImpl extends IBaseViewImpl {
    void onBeforeMvMonerySuccess(BeforeMvMoneyResponse beforeMvMoneyResponse);
    void onBeforeMvMoneryFailed(String msg);
}

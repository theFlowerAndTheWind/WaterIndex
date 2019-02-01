package com.jieshuizhibiao.waterindex.contract.view;

import com.jieshuizhibiao.waterindex.contract.IBaseViewImpl;

import java.util.Map;

/**
 * Created by songxiaotao on 2019/1/13.
 * Class Note:
 */

public interface AddTradeViewImpl extends IBaseViewImpl {
    void onEdtContentsLegal();
    void onEdtContentsIllegalSell(Map<String, Boolean> verify);
    void onEdtContentsIllegalBuy(Map<String, Boolean> verify);
    void onAddTradeSuccess();
    void onAddTradeFailed(String msg);
}

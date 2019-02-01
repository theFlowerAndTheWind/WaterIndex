package com.jieshuizhibiao.waterindex.contract.view;

import com.jieshuizhibiao.waterindex.beans.TradeDetailResponseBean;
import com.jieshuizhibiao.waterindex.contract.IBaseViewImpl;

/**
 * Created by WanghongHe on 2018/12/12 18:26.
 */

public interface TradeDetailViewImpl extends IBaseViewImpl {
    void onTradeDetailSuccess(TradeDetailResponseBean tradeDetailResponseBean);
    void onTradeDetailFailed(String msg);
}

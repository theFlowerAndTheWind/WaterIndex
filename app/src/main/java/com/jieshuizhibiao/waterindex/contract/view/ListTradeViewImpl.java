package com.jieshuizhibiao.waterindex.contract.view;

import com.jieshuizhibiao.waterindex.beans.ListTradeResponseBean;
import com.jieshuizhibiao.waterindex.contract.IBaseViewImpl;

import java.util.List;

/**
 * Created by songxiaotao on 2019/1/19.
 * Class Note:
 */

public interface ListTradeViewImpl extends IBaseViewImpl {
    void onListTradeSuccess(ListTradeResponseBean bean);
    void onloadMoreListTrade(List<ListTradeResponseBean.TradeList> tradeLists);
    void onRefreshListTrade(List<ListTradeResponseBean.TradeList> tradeLists);
    void onLoadListTradeError(boolean isRefresh, String msg);
}

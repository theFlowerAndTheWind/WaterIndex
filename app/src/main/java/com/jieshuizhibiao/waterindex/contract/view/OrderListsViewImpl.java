package com.jieshuizhibiao.waterindex.contract.view;

import com.jieshuizhibiao.waterindex.beans.OrderListsResponseBean;
import com.jieshuizhibiao.waterindex.contract.IBaseViewImpl;

/**
 * Created by WanghongHe on 2018/12/12 17:38.
 */

public interface OrderListsViewImpl extends IBaseViewImpl {
    void onOrderListSuccess(OrderListsResponseBean orderListBean);
    void onOrderListFailed(String msg);
}

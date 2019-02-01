package com.jieshuizhibiao.waterindex.contract.presenter;

import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.contract.BasePresenter;
import com.jieshuizhibiao.waterindex.contract.model.OrderListsModel;
import com.jieshuizhibiao.waterindex.contract.model.callback.CommonCallback;
import com.jieshuizhibiao.waterindex.contract.view.CommonViewImpl;

/**
 * Created by WanghongHe on 2018/12/12 17:39.
 */

public class OrderListsPresenter extends BasePresenter<CommonViewImpl> {

    private OrderListsModel orderListsModel;

    public OrderListsPresenter(OrderListsModel orderListsModel) {
        this.orderListsModel = orderListsModel;
    }

    public void getOrderList(BaseActivity activity, String type, String page, String page_size) {
        if (orderListsModel == null) {
            orderListsModel = new OrderListsModel();
        }
        orderListsModel.orderList(activity, type, page, page_size, new CommonCallback() {

            @Override
            public void onRequestSuccess(Object bean) {
                if (mView != null) {
                    mView.onRequestSuccess(bean);
                }
            }

            @Override
            public void onRequestFailed(String msg) {
                if (mView != null) {
                    mView.onRequestFailed(msg);
                }
            }
        });
    }
}

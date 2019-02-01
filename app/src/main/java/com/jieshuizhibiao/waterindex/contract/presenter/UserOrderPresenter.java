package com.jieshuizhibiao.waterindex.contract.presenter;

import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.contract.BasePresenter;
import com.jieshuizhibiao.waterindex.contract.model.UserOrderModel;
import com.jieshuizhibiao.waterindex.contract.model.callback.CommonCallback;
import com.jieshuizhibiao.waterindex.contract.view.CommonViewImpl;

public class UserOrderPresenter extends BasePresenter<CommonViewImpl> {
    private UserOrderModel userOrderModel;

    public UserOrderPresenter(UserOrderModel userOrderModel) {
        this.userOrderModel = userOrderModel;
    }

    public void createOrder(BaseActivity activity, String trade_id, String total) {
        if (userOrderModel == null) {
            userOrderModel = new UserOrderModel();
        }

        userOrderModel.createOrder(activity, trade_id, total, new CommonCallback() {
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
